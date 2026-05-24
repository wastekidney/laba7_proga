package ru.itmo.server.Commands;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddResponse;

import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class Authentication extends Command {
    private final CollectionManager collectionManager;
    public Authentication(CollectionManager collectionManager) {
        super("authentication", "проверить юзера на аутизм");
        this.collectionManager = collectionManager;
    }

    @Override
    public AddResponse execute(Request request) {
        var req = (AddRequest) request;
        User user = req.getUser();
        req.product.addUpdate(req.product, user.getId());
        StringBuilder sb = collectionManager.addStack(req.product);
        return new AddResponse(sb.toString(), "");
    }

}
