package ru.itmo.Commands;

import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.Managers.CommandManager;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Info extends Command{
    private final CollectionManager collectionManager;
    private final Console console;
    public Info(CollectionManager collectionManager, Console console) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            LocalDateTime time = collectionManager.getInitialisationTime();
            String typeOfCollection = collectionManager.getTypeOfCollection();
            Integer SizeOfCollection = collectionManager.getSizeOfCollection();
            console.print("время первой инициализации коллекции: " + time.toString() + "\nтип коллекции: " + typeOfCollection + "\nколичество элементов в коллекции: " + SizeOfCollection.toString());
        } catch (ElementException e) {
            console.println("в этой команде не должны быть элементы");
        }



    }
}
