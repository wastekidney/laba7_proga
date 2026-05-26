package ru.itmo.server.Commands;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.RemoveGreaterRequest;
import ru.itmo.common.network.request.RemoveLowerRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.RemoveGreaterResponse;
import ru.itmo.common.network.response.RemoveLowerResponse;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;
import java.util.List;

public class RemoveLower extends Command {
    private final CollectionManager collectionManager;
    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public RemoveLowerResponse execute(Request request) {
        var req = (RemoveLowerRequest) request;
        User user = req.getUser();
        float threshold;
        try {
            threshold = Float.parseFloat(req.price);
        } catch (NumberFormatException e) {
            return new RemoveLowerResponse("", "Некорректная цена");
        }
        try {
            int deletedCount = ProductDbManager.removeLowerByUser(threshold, user.getId());
            collectionManager.removeLowerByUser(threshold, user.getId());
            return new RemoveLowerResponse("Удалено элементов: " + deletedCount, "");
        } catch (SQLException e) {
            return new RemoveLowerResponse("", "Ошибка БД: " + e.getMessage());
        }
    }
}