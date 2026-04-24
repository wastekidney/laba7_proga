package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;
import ru.itmo.utils.NameFormatException;

import java.util.Scanner;

public class Add extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            Product product = new ProductInput(console).askProduct();
            if (product.validate()){
                collectionManager.addStack(product);
            } else {
                console.println("продукт не создан так как не соответсвует валидации");
            };
        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }

    }

}
