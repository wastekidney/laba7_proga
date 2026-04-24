package ru.itmo;

import ru.itmo.Collection.Organization;
import ru.itmo.Collection.Product;
import ru.itmo.Commands.*;
import ru.itmo.Console.*;
import ru.itmo.Console.Console;
import ru.itmo.Managers.CollectionManager;
import ru.itmo.Managers.CommandManager;
import ru.itmo.Managers.FileManager;
import ru.itmo.Managers.InputManager;
import ru.itmo.UserInput.InputFromFile.ScannerFile;

import java.io.*;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws IOException {
        Console console = new Console();
        Scanner scanner = new Scanner(System.in);
        ScannerFile.setScanner(scanner);
        CommandManager commandManager = new CommandManager();
        FileManager fileManager = new FileManager(console);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        InputManager inputManager = new InputManager(console, scanner, commandManager);
        collectionManager.loadCollection();
        Product.updateNextId(collectionManager);
        Organization.updateNextId(collectionManager);
        commandManager.register(new Add(console, collectionManager));
        commandManager.register(new Save(console, collectionManager));
        commandManager.register(new Show(console, collectionManager));
        commandManager.register(new Info(collectionManager, console));
        commandManager.register(new Clear(console, collectionManager));
        commandManager.register(new Help(console, commandManager));
        commandManager.register(new Exit(console));
        commandManager.register(new PrintFieldDescendingUnitOfMeasure(console));
        commandManager.register(new FilterContainsName(console, collectionManager));
        commandManager.register(new RemoveById(console, collectionManager));
        commandManager.register(new AddIfMin(console, collectionManager));
        commandManager.register(new PrintAscending(console, collectionManager));
        commandManager.register(new RemoveGreater(console, collectionManager));
        commandManager.register(new RemoveLower(console, collectionManager));
        commandManager.register(new UpdateId(collectionManager, console));
        commandManager.register(new ExecuteScript(console, inputManager));

        new InputManager(console, scanner, commandManager).asked(); // если мы инициализируем то и добавляем в класс, они private.
    }
}