package ru.itmo.server.networkUDP;

import org.apache.commons.lang3.SerializationUtils;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Managers.CommandManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class UDPServer {
    private final int port;
    private final CommandManager commandManager;
    private final ForkJoinPool readingPool = new ForkJoinPool();
    private final ExecutorService sendingPool = Executors.newCachedThreadPool();
    public UDPServer(int port, CommandManager commandManager) {
        this.port = port;
        this.commandManager = commandManager;
    }
    public void start() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP сервер запущен на порту " + port);
            while (true) {
                try {
                    byte[] buffer = new byte[65535];
                    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(receivePacket);
                    final byte[] requestBytes = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
                    final InetAddress clientAddress = receivePacket.getAddress();
                    final int clientPort = receivePacket.getPort();
                    readingPool.execute(() -> {
                        new Thread(() -> {
                            try {
                                Request request = SerializationUtils.deserialize(requestBytes);
                                Response response = commandManager.doing(request);
                                sendingPool.execute(() -> sendResponse(socket, response, clientAddress, clientPort));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendResponse(DatagramSocket socket, Response response, InetAddress address, int port) {
        try {
            byte[] responseBytes = SerializationUtils.serialize(response);
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}