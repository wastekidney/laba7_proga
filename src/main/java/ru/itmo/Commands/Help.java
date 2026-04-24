package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CommandManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

import java.util.Map;

public class Help extends Command{
    private final Console console;
    private final CommandManager commandManager;
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            var commands = commandManager.getCommands();
            for (Command command : commands) {
                console.println(command.toString());
            }
        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }
    }
}
