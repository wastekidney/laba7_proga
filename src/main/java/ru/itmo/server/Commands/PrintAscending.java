package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.PrintAscendingResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Collection.Product;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

import java.util.List;

public class PrintAscending extends Command {
    private final CollectionManager collectionManager;
    public PrintAscending(CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    @Override
    public PrintAscendingResponse execute(Request request) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbError = new StringBuilder();
        try {
            List<Product> CopyStackSortedByName = collectionManager.getCopyStackSortedByPrice();
            for (Product product : CopyStackSortedByName) {
                sb.append(product.toString()).append("\n");
            }
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new PrintAscendingResponse(sb.toString(), sbError.toString());
}
}
