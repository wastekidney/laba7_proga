package ru.itmo.Commands;


import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            collectionManager.showCollection();
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }

    }
}
