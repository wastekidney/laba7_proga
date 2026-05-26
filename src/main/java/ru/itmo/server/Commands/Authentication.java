package ru.itmo.server.Commands;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.request.AuthenticationRequest;
import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.AuthenticationResponse;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.DbUsersManager;

import java.sql.Connection;
import java.sql.SQLException;

import static ru.itmo.server.handlers.DatabaseHandler.getConnection;

public class Authentication extends Command {

    public Authentication() {
        super(
                "authentication",
                "авторизация пользователя (логин/пароль)"
        );
    }

    @Override
    public AuthenticationResponse execute(Request request) {

        try {

            AuthenticationRequest authRequest =
                    (AuthenticationRequest) request;

            User clientUser = authRequest.getUser();

            if (clientUser == null) {
                return new AuthenticationResponse(
                        "",
                        "Пользователь не передан"
                );
            }

            try (Connection connection = getConnection()) {

                // сначала пытаемся войти
                User authenticatedUser =
                        DbUsersManager.loginUser(
                                connection,
                                clientUser
                        );

                if (authenticatedUser != null) {

                    return new AuthenticationResponse(
                            "Успешный вход. Добро пожаловать, "
                                    + authenticatedUser.getName()
                                    + "!",
                            ""
                    );
                }

                // если вход не удался -> регистрируем
                User registeredUser =
                        DbUsersManager.registerUser(
                                connection,
                                clientUser
                        );

                if (registeredUser != null) {

                    return new AuthenticationResponse(
                            "Пользователь успешно зарегистрирован: "
                                    + registeredUser.getName(),
                            ""
                    );
                }

                return new AuthenticationResponse(
                        "",
                        "Не удалось выполнить авторизацию"
                );
            }

        } catch (SQLException e) {

            MainServer.logger.error(
                    "Ошибка авторизации",
                    e
            );

            return new AuthenticationResponse(
                    "",
                    "Ошибка БД: " + e.getMessage()
            );
        }
    }
}