package ru.itmo.server.Commands;


import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.network.response.ShowResponse;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CollectionManager;

public class Show extends Command {
    private final CollectionManager collectionManager;
    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public ShowResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            sb = collectionManager.showCollection();
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }

        return new ShowResponse(sb.toString(), sbError.toString());
    }
}
