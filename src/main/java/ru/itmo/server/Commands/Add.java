package ru.itmo.server.Commands;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddResponse;

import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;

public class Add extends Command {
    private final CollectionManager collectionManager;
    public Add(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public AddResponse execute(Request request) {
        var req = (AddRequest) request;
        User user = req.getUser();
        int userId = user.getId();
        try {
            Product saved = ProductDbManager.add(req.product, userId);
            collectionManager.addStack(saved);
            return new AddResponse("Элемент добавлен", "");
        } catch (SQLException e) {
            return new AddResponse("", "Ошибка: " + e.getMessage());
        }
    }

}
