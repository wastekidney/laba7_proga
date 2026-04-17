package ru.itmo.Commands;

import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.utils.EmptyInputException;

public class RemoveLower extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public RemoveLower(Console console, CollectionManager collectionManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new EmptyInputException();
            collectionManager.removeLower(element);
        } catch (EmptyInputException e) {
            console.printErr("цена не указана");
        }

    }
}