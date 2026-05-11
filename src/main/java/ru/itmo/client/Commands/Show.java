package ru.itmo.client.Commands;


import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.request.ShowRequest;
import ru.itmo.common.network.response.ShowResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.ElementException;

import java.util.Objects;

public class Show extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public Show(Console console, UDPClient udpClient) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            var response = (ShowResponse) udpClient.sendReceiveMessage(new ShowRequest());
            if (Objects.equals(response.getMessageError(), ""))
                console.println(response.getMessage());
            else
                console.println(response.getMessageError());
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }

    }
}
