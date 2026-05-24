package ru.itmo.server.Managers;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AuthenticationRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Commands.Command;
import ru.itmo.server.handlers.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class CommandManager {

    private final Map<String, Command> commands = new TreeMap<>();

    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }

    public Response doing(Request request) {
        if (!"help".equals(request.getName()) && !(request instanceof AuthenticationRequest)) {
            User user = request.getUser();
            if (user == null) {
                return new Response("", "Требуется аутентификация");
            }
            try (Connection connection = DatabaseHandler.getConnection()) {
                DbUsersManager.authenticateUser(connection, user);
            } catch (SQLException e) {
                return new Response("", "Неверные данные");
            }
        }

        Command command = commands.get(request.getName());
        if (command == null) {
            System.out.println("Команда '" + request.getName() + "' не найдена");
        }
        Response response = command.execute(request);
        return response;
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}