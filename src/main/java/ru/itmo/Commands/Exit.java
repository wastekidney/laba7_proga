package ru.itmo.Commands;

import ru.itmo.Console.Console;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;
import ru.itmo.utils.State;

public class Exit extends Command{
    private final Console console;
    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            console.print("это конец..");
        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }

    }
}
