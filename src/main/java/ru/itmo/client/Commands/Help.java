package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.HelpRequest;
import ru.itmo.common.network.response.HelpResponse;
import ru.itmo.server.Managers.CommandManager;
import ru.itmo.common.Exeption.ElementException;

import java.util.Objects;

public class Help extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public Help(Console console, UDPClient udpClient) {
        super("help", "вывести справку по доступным командам");
        this.udpClient = udpClient;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            var response = (HelpResponse) udpClient.sendReceiveMessage(new HelpRequest());
            if (Objects.equals(response.getMessageError(), ""))
                console.println(response.getMessage());
            else
                console.println(response.getMessageError());
        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }
    }
}
