package ru.itmo.server.Commands;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.request.UpdateIdRequest;
import ru.itmo.common.network.response.UpdateIdResponse;
import ru.itmo.common.Collection.Product;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;

public class UpdateId extends Command {
    private final CollectionManager collectionManager;
    public UpdateId(CollectionManager collectionManager) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public UpdateIdResponse execute(Request request) {
        var req = (UpdateIdRequest) request;
        User user = request.getUser();
        long id;
        try {
            id = Long.parseLong(req.id);
        } catch (NumberFormatException e) {
            return new UpdateIdResponse("", "id должен быть числом");
        }
        Product product = collectionManager.getById(id);
        if (product == null) {
            return new UpdateIdResponse("", "Продукт не найден");
        }
        if (product.getUserId() != user.getId()) {
            return new UpdateIdResponse("", "Вы не владелец");
        }
        try {
            ProductDbManager.update(id, req.newProduct, user.getId());
            req.newProduct.setId(id);
            collectionManager.updateProduct(req.newProduct);
            return new UpdateIdResponse("Продукт обновлён", "");
        } catch (SQLException e) {
            return new UpdateIdResponse("", "Ошибка БД: " + e.getMessage());
        }
    }

}
