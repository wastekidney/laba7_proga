package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.network.request.InfoRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.InfoResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.ElementException;

import java.time.LocalDateTime;

public class Info extends Command{
    private final UDPClient udpClient;
    private final Console console;
    public Info(Console console, UDPClient udpClient) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String element) {
        try {
            var response = (InfoResponse) udpClient.sendReceiveMessage(new InfoRequest(SessionHandler.getCurrentUser()));
            console.println(response.getInfo());

        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }



    }
}
