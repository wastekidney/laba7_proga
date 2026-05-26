package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.collectionForm.userForm.UserInput;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AuthenticationRequest;
import ru.itmo.common.network.response.AuthenticationResponse;
import ru.itmo.common.network.response.Response;

public class Authentication extends Command {

    private final Console console;
    private final UDPClient udpClient;

    public Authentication(Console console, UDPClient udpClient) {
        super("authentication", "аутентификация");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String element) {
        User user = new UserInput(console).askUser();
        if (!user.validate()) {
            console.println("неверные данные");
            return;
        }
        Response response = udpClient.sendReceiveMessage(new AuthenticationRequest(user));
        if (response instanceof AuthenticationResponse authResp) {
            if (authResp.getMessageError().isEmpty()) {
                SessionHandler.setCurrentUser(user);
                console.println(authResp.getMessage());
            } else {
                console.println(authResp.getMessageError());
            }
        } else {
            console.println("Ошибка: " + response.getMessageError());
        }
    }
}