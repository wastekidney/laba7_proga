package ru.itmo.server.Managers;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;
import ru.itmo.server.Commands.Command;
import ru.itmo.server.Commands.Help;

import java.util.*;

public class CommandManager {

    private final Map<String, Command> commands = new TreeMap<>();


    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }

//    public void doing(String name, String element){
//        Command command = commands.get(name);
//        if (command == null) {
//            System.out.println("Команда '" + name + "' не найдена");
//        } else {
//        command.execute(element);
//        }
//    }
    public Response doing(Request request){
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
