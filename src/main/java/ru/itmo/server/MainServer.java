package ru.itmo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.server.Commands.*;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.CommandManager;
import ru.itmo.server.handlers.DatabaseHandler;
import ru.itmo.server.networkUDP.UDPServer;

import java.io.IOException;

public class MainServer {
    public static final Logger logger = LoggerFactory.getLogger(MainServer.class);


    public static void main(String[] args) throws IOException {
        DatabaseHandler.configure(
                "jdbc:postgresql://pg:5432/studs",
                "s466072",
                "AgOUeo6JlsbltUHA"
        );
        CollectionManager collectionManager = new CollectionManager();
//        collectionManager.loadCollection();
//        Product.updateNextId(collectionManager);
//        Organization.updateNextId(collectionManager);
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
        commandManager.register(new Authentication());
        UDPServer udpServer = new UDPServer(1050, commandManager);

            try {
                udpServer.start();
            }  catch (Exception e) {
                MainServer.logger.error(e.getMessage());
            }
//        new Save(collectionManager).execute();

    }
}
