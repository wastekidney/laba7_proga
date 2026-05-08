package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.RemoveGreaterRequest;
import ru.itmo.common.network.response.RemoveGreaterResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.EmptyInputException;

public class RemoveGreater extends Command {
    private final Console console;
    private final UDPClient udpClient;
    public RemoveGreater(Console console, UDPClient udpClient) {
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        var response = (RemoveGreaterResponse) udpClient.sendReceiveMessage(new RemoveGreaterRequest(element));
        console.println(response.getMessage());
    }
}
