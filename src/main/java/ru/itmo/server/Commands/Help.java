package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.HelpResponse;
import ru.itmo.common.network.response.Response;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;
import ru.itmo.server.Managers.CommandManager;

public class Help extends Command {
    private final CommandManager commandManager;
    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public HelpResponse execute(Request request) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbError = new StringBuilder();
        try {
            var commands = commandManager.getCommands();
            for (Command command : commands) {
                sb.append(command.toString() + "\n");
            }
        } catch (Exception e) {
            String messageError = "ошибка: " + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new HelpResponse(sb.toString(), sbError.toString());
    }
}
