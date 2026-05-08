package ru.itmo.server.Commands;

import ru.itmo.common.network.request.RemoveByIdRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveByIdResponse;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class RemoveById extends Command {
    private final CollectionManager collectionManager;
    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", " удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public RemoveByIdResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            var req = (RemoveByIdRequest) request;
            collectionManager.removeById(req.id);
            sb.append("удален элемент с id: ").append(req.id);
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new RemoveByIdResponse(sb.toString(), sbError.toString());
    }
}
