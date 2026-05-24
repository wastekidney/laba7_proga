package ru.itmo.client.Commands;

import ru.itmo.client.MainClient;
import ru.itmo.client.collectionForm.userForm.UserInput;
import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.client.session.SessionHandler;
import ru.itmo.common.Collection.Product;
import ru.itmo.client.Console.Console;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AddRequest;
import ru.itmo.client.collectionForm.ProductInput;
import ru.itmo.common.network.request.AuthenticationRequest;
import ru.itmo.common.network.response.AddResponse;
import java.util.Objects;

public class Authentication extends Command{
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
        if (user.validate()){
            var response = (AddResponse) udpClient.sendReceiveMessage(new AuthenticationRequest(user));
            if (Objects.equals(response.getMessageError(), ""))
                console.println(response.getMessage());
            else
                console.println(response.getMessageError());
        } else {
            console.println("продукт не создан так как не соответсвует валидации");
            MainClient.logger.info("продукт не создан так как не соответсвует валидации");
        }
    }

}
