package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.NotFoundException;
import ru.itmo.UserInput.ProductInput;

import java.util.Scanner;

public class UpdateId extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public UpdateId(CollectionManager collectionManager, Console console) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element.isEmpty()) throw new EmptyInputException();
            Long CurrentId = Long.parseLong(element);
            Product product = collectionManager.getById(CurrentId);
            if (product == null) throw new NotFoundException();
            Product newProduct = new ProductInput(console).askProduct();
            product.update(newProduct);

        } catch (NotFoundException exception) {
            console.printErr("id не найден");
        } catch (EmptyInputException e) {
            console.printErr("файл не указан");

    }
    }
}
