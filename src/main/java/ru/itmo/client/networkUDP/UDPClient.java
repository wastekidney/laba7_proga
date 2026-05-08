package ru.itmo.client.networkUDP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.Arrays;

import org.apache.commons.lang3.SerializationUtils;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.request.ShowRequest;
import ru.itmo.common.network.response.Response;
import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

public class UDPClient{
    private InetAddress host;
    private int port;

    public UDPClient(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }

    public Response sendReceiveMessage(Request request) { // клиент
        try {
            final int chunkSize = 1400;
            DatagramChannel clientChannel = DatagramChannel.open();
            clientChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(host, port);
            byte[] sendingDataBuffer = SerializationUtils.serialize(request);

            int totalChunk = (sendingDataBuffer.length + chunkSize - 1) / chunkSize;
            for (int i = 0; i < totalChunk; i++) {
                int chunkArrayStartIndex = i * chunkSize;
                int sizeCurrentChunk = Math.min(chunkSize, sendingDataBuffer.length - chunkArrayStartIndex);
                byte[] chunk = Arrays.copyOfRange(sendingDataBuffer, chunkArrayStartIndex, chunkArrayStartIndex + sizeCurrentChunk);
                // DatagramPacket sendingPacket = new DatagramPacket(chunk, chunk.length, address, port); // Создает пакет датаграммы для отправки пакетов длины length на указанный номер порта на указанном узле
                ByteBuffer sendBuffer = ByteBuffer.wrap(chunk);
                while (sendBuffer.hasRemaining()) {
                    clientChannel.send(sendBuffer, address);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteBuffer receiveBuffer = ByteBuffer.allocate(chunkSize);

            long startTime = System.currentTimeMillis();
            while (true) {
                receiveBuffer.clear();
                InetSocketAddress from = (InetSocketAddress) clientChannel.receive(receiveBuffer);
                if (from != null) {
                    receiveBuffer.flip();
                    byte[] chunk = new byte[receiveBuffer.remaining()];
                    receiveBuffer.get(chunk);
                    baos.write(chunk);
                    if (chunk.length < chunkSize) break;
                } else {
                    if (System.currentTimeMillis() - startTime > 2000) {
                        throw new IOException();
                    }
                }
            }
            clientChannel.close();
            return SerializationUtils.deserialize(baos.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
