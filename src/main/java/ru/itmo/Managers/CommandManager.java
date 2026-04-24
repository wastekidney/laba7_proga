package ru.itmo.Managers;

import ru.itmo.Collection.Product;
import ru.itmo.Commands.Command;

import java.util.*;

public class CommandManager {

    private final Map<String, Command> commands = new TreeMap<>();


    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }
    public void doing(String name, String element){
        Command command = commands.get(name);
        if (command == null) {
            System.out.println("Команда '" + name + "' не найдена");
        } else {
        command.execute(element);
        }
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}
