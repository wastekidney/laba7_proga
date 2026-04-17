package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AddIfMin extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            Product product = (new ProductInput(console).askProduct());
            List<Product> CopyStackSortedPrice = collectionManager.getCopyStackSortedByPrice();
            if (product.getPrice() < CopyStackSortedPrice.stream().min(Comparator.comparing(Product::getPrice)).get().getPrice()) {
                collectionManager.addStack(product);
            }
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }
    }
}
