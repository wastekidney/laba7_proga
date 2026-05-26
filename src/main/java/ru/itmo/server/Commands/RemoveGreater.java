package ru.itmo.server.Commands;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.RemoveGreaterRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveGreaterResponse;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;
import java.util.List;

public class RemoveGreater extends Command {

    private final CollectionManager collectionManager;
    public RemoveGreater(CollectionManager collectionManager) {
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }
    @Override
    public RemoveGreaterResponse execute(Request request) {
        var req = (RemoveGreaterRequest) request;
        User user = req.getUser();
        float threshold;
        try {
            threshold = Float.parseFloat(req.price);
        } catch (NumberFormatException e) {
            return new RemoveGreaterResponse("", "Некорректная цена");
        }
        try {
            int deletedCount = ProductDbManager.removeGreaterByUser(threshold, user.getId());
            collectionManager.removeGreaterByUser(threshold, user.getId());
            return new RemoveGreaterResponse("Удалено элементов: " + deletedCount, "");
        } catch (SQLException e) {
            return new RemoveGreaterResponse("", "Ошибка БД: " + e.getMessage());
        }
    }
}
