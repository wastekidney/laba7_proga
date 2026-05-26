package ru.itmo.client.networkUDP;

import org.apache.commons.lang3.SerializationUtils;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPClient {

    private final InetAddress host;
    private final int port;

    public UDPClient(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendReceiveMessage(Request request) {

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);
            byte[] requestBytes = SerializationUtils.serialize(request);
            DatagramPacket sendPacket = new DatagramPacket(requestBytes, requestBytes.length, host, port);

            socket.send(sendPacket);
            byte[] buffer = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            socket.receive(receivePacket);

            byte[] responseBytes = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
            return SerializationUtils.deserialize(responseBytes);
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(
                    "сервер не отвечает");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}