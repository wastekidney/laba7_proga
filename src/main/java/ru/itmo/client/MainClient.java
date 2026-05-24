package ru.itmo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.client.Console.Console;
import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.client.InputManager.InputManager;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.HelpRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainClient {
    public static final Logger logger = LoggerFactory.getLogger(MainClient.class);
    private static final int port = 1050;
    public static void main(String[] args) throws IOException {

        Console console = new Console();
        Scanner scanner = new Scanner(System.in);
        ScannerFile.setScanner(scanner);
        //        Product.updateNextId(collectionManager);
//        Organization.updateNextId(collectionManager);
        try {
            UDPClient udpClient = new UDPClient(InetAddress.getLoopbackAddress(), port);
            udpClient.sendReceiveMessage(new HelpRequest());
            InputManager inputManager = new InputManager(console, scanner, udpClient);
            inputManager.asked();
        } catch (Exception e){
            logger.info("Невозможно подключиться к серверу.", e);
            System.out.println("Невозможно подключиться к серверу!");
        }

    }
}
