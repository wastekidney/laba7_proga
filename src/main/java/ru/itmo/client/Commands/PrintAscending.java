package ru.itmo.client.Commands;

import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.Collection.Product;
import ru.itmo.client.Console.Console;
import ru.itmo.common.network.request.PrintAscendingRequest;
import ru.itmo.common.network.response.PrintAscendingResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.ElementException;

import java.util.List;
import java.util.Objects;

public class PrintAscending extends Command {
    private final Console console;
    private final UDPClient udpClient;
    public PrintAscending(Console console, UDPClient udpClient) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        var response = (PrintAscendingResponse) udpClient.sendReceiveMessage(new PrintAscendingRequest(SessionHandler.getCurrentUser()));
        if (Objects.equals(response.getMessageError(), ""))
            console.println(response.getMessage());
        else
            console.println(response.getMessageError());
    }
}
