package ru.itmo.client.Commands;

import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.Collection.Product;
import ru.itmo.client.Console.Console;
import ru.itmo.common.network.request.AddIfMinRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AddIfMinResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.client.CollectionForm.ProductInput;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.networkUDP.UDPServer;

import java.util.Comparator;
import java.util.List;

public class AddIfMin extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public AddIfMin(Console console, UDPClient udpClient) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String  element) {
        Product product = (new ProductInput(console).askProduct());
        if (product.validate()){
            var response = (AddIfMinResponse) udpClient.sendReceiveMessage(new AddIfMinRequest(product));
            console.println(response.getMessage());
        } else {
            console.println("продукт не валидный");
        }
    }
}
