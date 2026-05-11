package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.RemoveLowerRequest;
import ru.itmo.common.network.response.RemoveLowerResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.EmptyInputException;

import java.util.Objects;

public class RemoveLower extends Command {
    private final Console console;
    private final UDPClient udpClient;
    public RemoveLower(Console console,  UDPClient udpClient) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        var response = (RemoveLowerResponse) udpClient.sendReceiveMessage(new RemoveLowerRequest(element));
        if (Objects.equals(response.getMessageError(), ""))
            console.println(response.getMessage());
        else
            console.println(response.getMessageError());

    }
}