package ru.itmo.server.Commands;

import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.RemoveGreaterRequest;
import ru.itmo.common.network.request.RemoveLowerRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveGreaterResponse;
import ru.itmo.common.network.response.RemoveLowerResponse;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class RemoveLower extends Command {
    private final CollectionManager collectionManager;
    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public RemoveLowerResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            var req = (RemoveLowerRequest) request;
            collectionManager.removeLower(req.price);
            sb.append("элементы коллекции, чья цена меньше ").append(req.price).append(" были удалены");
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new RemoveLowerResponse(sb.toString(), sbError.toString());

    }
}