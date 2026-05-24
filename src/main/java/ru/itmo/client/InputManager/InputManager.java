package ru.itmo.client.InputManager;

import ru.itmo.client.Commands.Command;
import ru.itmo.client.MainClient;
import ru.itmo.client.networkUDP.UDPClient;
//import ru.itmo.server.Managers.CommandManager;
import ru.itmo.client.Console.Console;
import ru.itmo.client.Commands.*;
import ru.itmo.common.Exeption.ScriptRecursionException;
import ru.itmo.client.InputFromFile.ScannerFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class InputManager {

    public enum State {
        EXIT,
        Continue,
        ERROR
    }
    private final Map<String, Command> commands;
    private final Console console;
    private final Scanner scanner;
    private final List<String> scriptStack = new ArrayList<>();
    private final UDPClient client;

    public static State state = State.Continue;

    public InputManager(Console console, Scanner scanner, UDPClient client) {
        this.console = console;
        this.scanner = scanner;
        this.client = client;

        this.commands = new TreeMap<>(){{
            put("add",new Add(console, client));
            put("info",new Info(console,client));
            put("show", new Show(console, client));
            put("clear",new Clear(console,client));
            put("help", new Help(console,client));
            put("add_if_min", new AddIfMin(console, client));
            put("filter_contains_name", new FilterContainsName(console, client));
            put("update_id", new UpdateId(client, console));
            put("print_field_descending_unit_of_measure",  new PrintFieldDescendingUnitOfMeasure(console, client));
            put("print_ascending",  new PrintAscending(console, client));
            put("remove_by_id",  new RemoveById(console, client));
            put("remove_greater",   new RemoveGreater(console, client));
            put("remove_lower",     new RemoveLower(console, client));
            put("execute_script", new ExecuteScript(console, InputManager.this));
            put("authentication", new Authentication(console, client));
        }};
    }


    public void asked(){
        String commandName;
        String command;
        do {
            console.println("введите команду");
            MainClient.logger.info("введите команду");
            String commandElement = null;
            command = ScannerFile.getScanner().nextLine().trim();
            if (command.isEmpty()) continue;

            var commandSplit = command.split(" ", 2);
            commandName = commandSplit[0].trim().toLowerCase();
            if (!command.equals(commandName)) {
                commandElement = commandSplit[1].trim().toLowerCase();
            }
            try {
                Command commandCommand =  commands.get(commandName);
                commandCommand.execute(commandElement);
            } catch (NullPointerException e){
                console.printErr("такой команды не существует");
                MainClient.logger.info("такой команды не существует");
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
                Command commandCommand =  commands.get(commandName);
                commandCommand.execute(commandElement);

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

