package ru.itmo.server.Commands;

import ru.itmo.client.CollectionForm.ProductInput;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.request.UpdateIdRequest;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.network.response.UpdateIdResponse;
import ru.itmo.common.Collection.Product;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.common.Exeption.NotFoundException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class UpdateId extends Command {
    private final CollectionManager collectionManager;
    public UpdateId(CollectionManager collectionManager) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public UpdateIdResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            var req = (UpdateIdRequest) request;
            Long CurrentId = Long.parseLong(req.id);
            Product product = collectionManager.getById(CurrentId);
            product.update(req.newProduct);
            sb.append("продукт обновлен");
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new UpdateIdResponse(sb.toString(), sbError.toString());
    }

}
