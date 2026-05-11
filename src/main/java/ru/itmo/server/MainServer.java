package ru.itmo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.common.Collection.Organization;
import ru.itmo.common.Collection.Product;
import ru.itmo.server.Commands.*;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.CommandManager;
import ru.itmo.server.Managers.FileManager;
import ru.itmo.server.networkUDP.UDPServer;

import java.io.IOException;
import java.net.InetAddress;

public class MainServer {
    public static final Logger logger = LoggerFactory.getLogger(MainServer.class);
    public static void main(String[] args) throws IOException {


        FileManager fileManager = new FileManager();
        CollectionManager collectionManager = new CollectionManager(fileManager);
        collectionManager.loadCollection();
        Product.updateNextId(collectionManager);
        Organization.updateNextId(collectionManager);
        CommandManager commandManager = new CommandManager();
        commandManager.register(new Add(collectionManager));
        commandManager.register(new Show(collectionManager));
        commandManager.register(new Info(collectionManager));
        commandManager.register(new Clear(collectionManager));
        commandManager.register(new Help(commandManager));
        commandManager.register(new PrintFieldDescendingUnitOfMeasure());
        commandManager.register(new FilterContainsName(collectionManager));
        commandManager.register(new RemoveById(collectionManager));
        commandManager.register(new AddIfMin(collectionManager));
        commandManager.register(new PrintAscending(collectionManager));
        commandManager.register(new RemoveGreater(collectionManager));
        commandManager.register(new RemoveLower(collectionManager));
        commandManager.register(new UpdateId(collectionManager));
        UDPServer udpServer = new UDPServer(InetAddress.getLoopbackAddress(), 1050, commandManager, collectionManager);
        while (true) {
            try {
                udpServer.receiveSendMessage();
            }  catch (Exception e) {
                MainServer.logger.error(e.getMessage());
                break;
            }
        }
        new Save(collectionManager).execute();

    }
}
