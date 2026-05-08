package ru.itmo.server.Commands;

import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.RemoveGreaterRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveGreaterResponse;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class RemoveGreater extends Command {

    private final CollectionManager collectionManager;
    public RemoveGreater(CollectionManager collectionManager) {
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }
    @Override
    public RemoveGreaterResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            var req = (RemoveGreaterRequest) request;
            collectionManager.removeGreater(req.price);
            sb.append("элементы коллекции, чья цена больше ").append(req.price).append(" были удалены");
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new RemoveGreaterResponse(sb.toString(), sbError.toString());
    }
}
