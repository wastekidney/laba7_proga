package ru.itmo.server.networkUDP;

import org.apache.commons.lang3.SerializationUtils;
import ru.itmo.client.Commands.Command;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.CommandManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

public class UDPServer {
//    private byte[] buf = new byte[256];
    private static final int chunkSize = 1400;

    private final InetAddress host;
    private final int port;
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private long lastRequestTime = System.currentTimeMillis();
    public UDPServer(InetAddress host, int port, CommandManager commandManager, CollectionManager collectionManager) throws SocketException {
        this.host = host;
        this.port = port;
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }
    public void receiveSendMessage() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(port, host);
        serverSocket.setSoTimeout(5000);
        while (true) {
            ByteArrayOutputStream requestBuffer = new ByteArrayOutputStream();
            byte[] receiveBuffer = new byte[chunkSize];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            try {
                serverSocket.receive(receivePacket); // Получите данные от клиента и сохраните их в packet
            }  catch (SocketTimeoutException e) {
                if (System.currentTimeMillis() - lastRequestTime > 100000) {
                    collectionManager.saveCollection();
                    System.exit(0);
                }
                continue;
            }
            lastRequestTime = System.currentTimeMillis();
            requestBuffer.write(receivePacket.getData(), 0, receivePacket.getLength());

            InetAddress address = receivePacket.getAddress();
            int port = receivePacket.getPort();

            if (receivePacket.getLength() >= chunkSize) {
                while (true) {
                    byte[] buffer = new byte[chunkSize];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        serverSocket.receive(packet);
                    } catch (SocketTimeoutException e) {
                        break;
                    }
                    requestBuffer.write(packet.getData(), 0, packet.getLength());
                    if (packet.getLength() < chunkSize) {
                        break;
                    }
                }
            }
            byte[] requestBytes = requestBuffer.toByteArray();
            Request request = SerializationUtils.deserialize(requestBytes);

            Response response = processRequest(request);

            byte[] responseData = SerializationUtils.serialize((java.io.Serializable) response);
            int totalChunks = (responseData.length + chunkSize - 1) / chunkSize;

            for (int i = 0; i < totalChunks; i++) {
                int from = i * chunkSize;
                int len = Math.min(chunkSize, responseData.length - from);
                byte[] chunk = new byte[len];
                System.arraycopy(responseData, from, chunk, 0, len);
                DatagramPacket packet = new DatagramPacket(chunk, chunk.length, address, port);
                serverSocket.send(packet);
            }
        }

    }

    private Response processRequest(Request request) {
        Response response = commandManager.doing(request);
        return  response;
    }
}
