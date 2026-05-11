package ru.itmo.server.Commands;

import ru.itmo.client.CollectionForm.ProductInput;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddIfMinResponse;
import ru.itmo.common.network.response.AddResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Collection.Product;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

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
        StringBuilder sb = null;
        var req = (AddRequest) request;
        req.product.setNextId(Product.nextId);
        MainServer.logger.info(String.valueOf(Product.nextId));
        List<Product> CopyStackSortedPrice = collectionManager.getCopyStackSortedByPrice();
        if (req.product.getPrice() < CopyStackSortedPrice.stream().min(Comparator.comparing(Product::getPrice)).get().getPrice()) {
            sb = collectionManager.addStack(req.product);
        }
        assert sb != null;
        return new AddIfMinResponse(sb.toString(), "");
    }
}
