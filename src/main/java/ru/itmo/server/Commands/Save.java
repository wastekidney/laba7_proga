package ru.itmo.server.Commands;

import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class Save{
    private final CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public void execute() {
        try {
            collectionManager.saveCollection();
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.error(messageError);
        }
    }
}
