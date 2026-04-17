package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.Managers.CommandManager;
import ru.itmo.Managers.FileManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

public class Save extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            collectionManager.saveCollection();
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }
    }
}
