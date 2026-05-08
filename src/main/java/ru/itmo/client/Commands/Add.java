package ru.itmo.client.Commands;

import ru.itmo.client.MainClient;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.Collection.Product;
import ru.itmo.client.Console.Console;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.client.CollectionForm.ProductInput;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.response.AddResponse;
import ru.itmo.common.network.response.InfoResponse;

public class Add extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public Add(Console console, UDPClient udpClient) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String element) {
        Product product = new ProductInput(console).askProduct();
        if (product.validate()){
            var response = (AddResponse) udpClient.sendReceiveMessage(new AddRequest(product));
            console.println(response.getMessage());
        } else {
            console.println("продукт не создан так как не соответсвует валидации");
            MainClient.logger.info("продукт не создан так как не соответсвует валидации");
        }
    }

}
