package ru.itmo.server.Commands;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddIfMinResponse;
import ru.itmo.common.Collection.Product;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.server.Managers.ProductDbManager;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AddIfMin extends Command {
    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public AddIfMinResponse execute(Request request) {
        var req = (AddRequest) request;
        User user = req.getUser();
        int userId = user.getId();
        var stack = collectionManager.getStack();
        if (stack.isEmpty()) {
            try {
                ProductDbManager.add(req.product, userId);
                collectionManager.addStack(req.product);
                return new AddIfMinResponse("Элемент добавлен (коллекция была пуста)", "");
            } catch (SQLException e) {
                return new AddIfMinResponse("", "Ошибка БД: " + e.getMessage());
            }
        } else {
            Product minProduct = stack.stream()
                    .min(Comparator.comparing(Product::getPrice))
                    .orElse(null);
            if (minProduct != null && req.product.getPrice() < minProduct.getPrice()) {
                try {
                    ProductDbManager.add(req.product, userId);
                    collectionManager.addStack(req.product);
                    return new AddIfMinResponse("Элемент добавлен, так как его цена меньше минимальной", "");
                } catch (SQLException e) {
                    return new AddIfMinResponse("", "Ошибка БД: " + e.getMessage());
                }
            } else {
                return new AddIfMinResponse("Элемент не добавлен: его цена не меньше минимальной", "");
            }
        }
    }
}
