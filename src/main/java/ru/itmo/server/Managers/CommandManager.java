package ru.itmo.server.Managers;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Commands.Command;
import ru.itmo.server.handlers.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class CommandManager {

    private final Map<String, Command> commands =
            new TreeMap<>();

    public void register(Command command) {
        commands.put(
                command.getCommandName(),
                command
        );
    }

    public Response doing(Request request) {

        try {

            if (request == null) {
                return new Response(
                        "",
                        "Пустой запрос"
                );
            }

            String commandName = request.getName();

            if (commandName == null) {
                return new Response(
                        "",
                        "Имя команды отсутствует"
                );
            }

            // authentication НЕ проверяем
            // иначе будет бесконечная проверка
            if (!commandName.equals("authentication")) {

                User user = request.getUser();

                if (user == null) {

                    return new Response(
                            "",
                            "Требуется авторизация"
                    );
                }

                try (
                        Connection connection =
                                DatabaseHandler.getConnection()
                ) {

                    User authenticatedUser =
                            DbUsersManager.loginUser(
                                    connection,
                                    user
                            );

                    if (authenticatedUser == null) {

                        return new Response(
                                "",
                                "Неверный логин или пароль"
                        );
                    }

                    // обновляем id пользователя
                    user.setId(
                            authenticatedUser.getId()
                    );
                }
            }

            Command command = commands.get(commandName);

            if (command == null) {

                return new Response(
                        "",
                        "Команда '" + commandName + "' не найдена"
                );
            }

            return command.execute(request);

        } catch (SQLException e) {

            return new Response(
                    "",
                    "Ошибка БД: " + e.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new Response(
                    "",
                    "Ошибка сервера: " + e.getMessage()
            );
        }
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}