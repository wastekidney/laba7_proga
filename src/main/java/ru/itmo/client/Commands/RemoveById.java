package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.RemoveByIdRequest;
import ru.itmo.common.network.response.RemoveByIdResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.ElementException;

import java.util.Objects;

public class RemoveById extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public RemoveById(Console console, UDPClient udpClient) {
        super("remove_by_id", " удалить элемент из коллекции по его id");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        var response = (RemoveByIdResponse) udpClient.sendReceiveMessage(new RemoveByIdRequest(element));
        if (Objects.equals(response.getMessageError(), ""))
            console.println(response.getMessage());
        else
            console.println(response.getMessageError());
    }
}
