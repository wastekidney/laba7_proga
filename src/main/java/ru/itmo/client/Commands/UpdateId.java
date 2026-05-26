package ru.itmo.client.Commands;

import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.Collection.Product;
import ru.itmo.client.Console.Console;
import ru.itmo.common.network.request.UpdateIdRequest;
import ru.itmo.common.network.response.UpdateIdResponse;
import ru.itmo.client.collectionForm.ProductInput;

import java.util.Objects;

public class UpdateId extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public UpdateId(UDPClient udpClient, Console console) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        Product newProduct = new ProductInput(console).askProduct();
        var response = (UpdateIdResponse) udpClient.sendReceiveMessage(new UpdateIdRequest(element, newProduct, SessionHandler.getCurrentUser()));
        if (Objects.equals(response.getMessageError(), ""))
            console.println(response.getMessage());
        else
            console.println(response.getMessageError());
    }
}
