package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.Response;

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

    public  abstract Response execute(Request request);
    @Override
    public String toString(){
        return "\u001B[32m\u001B[1m"+ commandName+": \u001B[0m" +description;
    }

}
