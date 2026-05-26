package ru.itmo.server.Commands;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.ClearResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;
import java.util.List;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public ClearResponse execute(Request request) {
        User user = request.getUser();
        try {
            int deletedCount = ProductDbManager.clearByUser(user.getId());
            collectionManager.clearByUser(user.getId());
            return new ClearResponse("Удалено ваших объектов: " + deletedCount, "");
        } catch (SQLException e) {
            return new ClearResponse("", "Ошибка БД: " + e.getMessage());
        }
    }

}
