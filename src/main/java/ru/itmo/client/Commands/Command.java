package ru.itmo.client.Commands;

import java.io.Serializable;

public abstract class Command implements Serializable {
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
    @Override
    public String toString(){
        return "\u001B[32m\u001B[1m"+ commandName+": \u001B[0m" +description;
    }

}
