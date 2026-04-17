package ru.itmo.Managers;

import ru.itmo.Collection.Product;
import ru.itmo.Commands.Command;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();


    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }
    public void doing(String name, String element){
        Command command = commands.get(name);
        command.execute(element);
    }

    public Map getCommands() {
        return commands;
    }
}
