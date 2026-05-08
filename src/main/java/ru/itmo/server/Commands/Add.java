package ru.itmo.server.Commands;

import ru.itmo.client.CollectionForm.ProductInput;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddResponse;
import ru.itmo.common.network.response.Response;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.Managers.CollectionManager;

public class Add extends Command {
    private final CollectionManager collectionManager;
    public Add(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public AddResponse execute(Request request) {
        var req = (AddRequest) request;
        StringBuilder sb =  collectionManager.addStack(req.product);
        return new AddResponse(sb.toString(), null);

    }

}
