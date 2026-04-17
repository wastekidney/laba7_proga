package ru.itmo.Commands;

import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.utils.EmptyInputException;

public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element == null) throw new EmptyInputException();
            collectionManager.removeGreater(element);
        } catch (EmptyInputException e) {
            console.printErr("цена не указана");
        }
    }
}
