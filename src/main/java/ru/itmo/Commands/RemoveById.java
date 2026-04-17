package ru.itmo.Commands;

import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

public class RemoveById extends Command{
    private final CollectionManager collectionManager;
    private final Console console;
    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id", " удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element == null) throw new ElementException();
            collectionManager.removeById(element);
        } catch (ElementException e) {
            console.println("надо ввести id");
        }
    }
}
