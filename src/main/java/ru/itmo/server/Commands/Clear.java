package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.ClearResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public ClearResponse execute(Request request) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbError = new StringBuilder();
        try {
            collectionManager.clearStack();
            sb.append("коллекция отчищена");
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
    }
        return new ClearResponse(sb.toString(), sbError.toString());
    }

}
