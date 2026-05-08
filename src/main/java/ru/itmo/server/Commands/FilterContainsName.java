package ru.itmo.server.Commands;

import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.FilterContainsNameRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.FilterContainsNameResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class FilterContainsName extends Command {

    private final CollectionManager collectionManager;
    public FilterContainsName(CollectionManager collectionManager) {
        super("filter_contains_name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
    }

    @Override
    public FilterContainsNameResponse execute(Request request) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbError = new StringBuilder();
        try {
            var req = (FilterContainsNameRequest) request;
            sb = collectionManager.filterContainsName(req.substringName);
        } catch (Exception e) {
        String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new FilterContainsNameResponse(sb.toString(), sbError.toString());
    }
}
