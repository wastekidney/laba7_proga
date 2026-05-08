package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.MainClient;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.common.network.request.ClearRequest;
import ru.itmo.common.network.response.AddResponse;
import ru.itmo.common.network.response.ClearResponse;
import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Exeption.ElementException;

public class Clear extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public Clear(Console console, UDPClient udpClient) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            var response = (ClearResponse) udpClient.sendReceiveMessage(new ClearRequest());
            console.print(response.getMessage());
        } catch (Exception e) {
            console.println("в этой команде не должны быть элементы");
            MainClient.logger.info(e.getMessage());
    }
    }
}
