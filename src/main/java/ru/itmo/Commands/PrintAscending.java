package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

import java.util.List;

public class PrintAscending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public PrintAscending(Console console, CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            List<Product> CopyStackSortedByName = collectionManager.getCopyStackSortedByPrice();
            for (Product product : CopyStackSortedByName) {
                System.out.println(product);}
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }

    }
}
