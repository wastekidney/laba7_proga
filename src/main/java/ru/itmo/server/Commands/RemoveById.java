package ru.itmo.server.Commands;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.RemoveByIdRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveByIdResponse;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;

public class RemoveById extends Command {
    private final CollectionManager collectionManager;
    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", " удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public RemoveByIdResponse execute(Request request) {
        var req = (RemoveByIdRequest) request;
        User user = request.getUser();
        long id;
        try {
            id = Long.parseLong(req.id);
        } catch (NumberFormatException e) {
            return new RemoveByIdResponse("", "id должен быть числом");
        }
        try {
            boolean deleted = ProductDbManager.deleteById(id, user.getId());
            if (!deleted) {
                return new RemoveByIdResponse("", "Объект не найден или не принадлежит вам");
            }
            collectionManager.removeProductById(id, user.getId());
            return new RemoveByIdResponse("Элемент удалён", "");
        } catch (SQLException e) {
            return new RemoveByIdResponse("", "Ошибка: " + e.getMessage());
        }
    }
}
