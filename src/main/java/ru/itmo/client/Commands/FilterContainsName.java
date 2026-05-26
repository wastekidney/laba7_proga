package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.network.request.FilterContainsNameRequest;
import ru.itmo.common.network.response.FilterContainsNameResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.EmptyInputException;

import java.util.Objects;

public class FilterContainsName extends Command {
    private final Console console;
    private final UDPClient udpClient;
    public FilterContainsName(Console console, UDPClient udpClient) {
        super("filter_contains_name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        var response = (FilterContainsNameResponse) udpClient.sendReceiveMessage(new FilterContainsNameRequest(element, SessionHandler.getCurrentUser()));
        if (Objects.equals(response.getMessageError(), ""))
            console.println(response.getMessage());
        else
            console.println(response.getMessageError());
    }
}
