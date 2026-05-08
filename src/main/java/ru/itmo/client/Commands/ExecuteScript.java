package ru.itmo.client.Commands;

import ru.itmo.client.Console.Console;
import ru.itmo.client.InputManager.InputManager;
import ru.itmo.common.Exeption.EmptyInputException;

public class ExecuteScript extends Command{
    private final Console console;
    private final InputManager inputManager;
    public ExecuteScript(Console console, InputManager inputManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла.");
        this.inputManager = inputManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element == null) throw new EmptyInputException();
            inputManager.script(element);
        } catch (EmptyInputException e) {
            console.printErr("файл не указан");
        }

    }
}
