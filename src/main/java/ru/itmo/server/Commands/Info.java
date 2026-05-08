package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.InfoResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

import java.time.LocalDateTime;

public class Info extends Command {
    private final CollectionManager collectionManager;
    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public InfoResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            LocalDateTime time = collectionManager.getInitialisationTime();
            String typeOfCollection = collectionManager.getTypeOfCollection();
            Integer SizeOfCollection = collectionManager.getSizeOfCollection();
            sb.append("время первой инициализации коллекции: " + time.toString() + "\nтип коллекции: " + typeOfCollection + "\nколичество элементов в коллекции: " + SizeOfCollection.toString());
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }

        return new InfoResponse(sb.toString(), sbError.toString());
    }
}
