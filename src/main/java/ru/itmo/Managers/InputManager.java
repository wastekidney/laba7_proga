package ru.itmo.Managers;

import ru.itmo.Console.Console;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.NonExistentCommandExeption;
import ru.itmo.utils.ScriptRecursionException;
import ru.itmo.utils.State;
import ru.itmo.UserInput.InputFromFile.ScannerFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class InputManager {
    private final Console console;
    private final Scanner scanner;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();

    public static State state = State.Continue;

    public InputManager(Console console, Scanner scanner, CommandManager commandManager) {
        this.console = console;
        this.scanner = scanner;
        this.commandManager = commandManager;
    }


    public void asked(){
        String commandName;
        String command;
        do {
            console.println("введите команду");
            String commandElement = null;
            command = ScannerFile.getScanner().nextLine().trim();
            if (command.isEmpty()) continue;

            var commandSplit = command.split(" ", 2);
            commandName = commandSplit[0].trim().toLowerCase();
            if (!command.equals(commandName)) {
                commandElement = commandSplit[1].trim().toLowerCase();
            }
            try {
                commandManager.doing(commandName, commandElement);
            } catch (NullPointerException e){
                console.printErr("такой команды не существует");
            }

            if (commandName.equals("exit")) {
                state = State.EXIT;
            }
        } while (state == State.Continue);
    }

    public void script(String nameFile) {
        scriptStack.add(nameFile); //рекурсия
        String commandName = "";
        String commandElement;
        File file = new File(nameFile);
        if (!file.exists()) {
            file = new File("../" + nameFile);
        }


        try (Scanner scriptScanner = new Scanner(file)) {
            ScannerFile.setUseFile();
            ScannerFile.setScanner(scriptScanner);
            if (!scriptScanner.hasNextLine()) {
                console.printErr("Файл скрипта пуст");
                return;
            }
            do {
                commandElement = null;
                String command;

                if (scriptScanner.hasNextLine()) {
                command = scriptScanner.nextLine().trim();
                if (command.isEmpty()) {continue;}
                var commandSplit = command.split(" ", 2);
                commandName = commandSplit[0].trim().toLowerCase();

                if (!command.equals(commandName)) {
                    commandElement = commandSplit[1].trim().toLowerCase();
                }} else {break;}

                while (scriptScanner.hasNextLine() && commandName.isEmpty()){
                    commandElement = null;
                    command = scriptScanner.nextLine().trim();
                    if (command.isEmpty()) {continue;}
                    var commandSplit = command.split(" ", 2);
                    commandName = commandSplit[0].trim().toLowerCase();
                    if (!command.equals(commandName)) {
                        commandElement = commandSplit[1].trim().toLowerCase();
                    }
                    }
                if (commandName.equals("execute_script")) {
                    for (String script:  scriptStack) {
                        if (commandElement.equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandManager.doing(commandName, commandElement);

                if (commandName.equals("exit")) {
                    state = State.EXIT;
                }
            } while (state != State.EXIT);

        } catch (FileNotFoundException e) {
            console.printErr("файл не найден");
        } catch (ScriptRecursionException e){
            console.printErr("файл вызывает рекурсию");
        } finally {
            scriptStack.removeLast();
            ScannerFile.setScanner(this.scanner);
            ScannerFile.setNoUseFile();
        }
    }
}

