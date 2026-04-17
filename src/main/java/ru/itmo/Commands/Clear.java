package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.Managers.CommandManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

import java.util.Scanner;

public class Clear extends Command{
    private final CollectionManager collectionManager;
    private final Console console;
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            collectionManager.clearStack();
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
    }
    }
}
