package ru.itmo.Commands;

import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.utils.EmptyInputException;

public class FilterContainsName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public FilterContainsName(Console console, CollectionManager collectionManager) {
        super("filter_contains_name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element == null) throw new EmptyInputException();
            collectionManager.filterContainsName(element);
        } catch (EmptyInputException e) {
            System.err.println("файл не указан");
        }
    }
}
