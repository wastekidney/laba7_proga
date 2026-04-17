package ru.itmo.Commands;

import ru.itmo.Collection.Product;

public abstract class Command {
    private final String commandName;
    private final String description;

    public Command(String commandName, String description) {
        this.commandName = commandName;
        this.description = description;
    }

    public String getCommandName() {
        return commandName;
    }
    public String getDescription() {
        return description;
    }

    public  abstract  void execute(String element);

}
