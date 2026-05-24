package ru.itmo.client.collectionForm.userForm;

import ru.itmo.client.Console.Console;
import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Collection.User.User;
import ru.itmo.common.Exeption.EmptyInputException;

public class UserInput {
    private final Console console;

    public UserInput(Console console) {
        this.console = console;
    }

    public User askUser() {
        User user = new User(0, askName(), askPassword());
        return  user;
    }
    public String askName(){
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                    console.print("введите ваше имя: ");
                }
                String Name = ScannerFile.getScanner().nextLine();
                if (Name.isEmpty() && !ScannerFile.getUseFile()) {
                    throw new EmptyInputException();
                }
                return Name;
            } catch (EmptyInputException exception) {
                console.printErr("имя не может быть пустым");
        }
    }}
    public String askPassword(){
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                    console.print("введите пароль");
                }
                String password = ScannerFile.getScanner().nextLine();
                if (password.isEmpty() && !ScannerFile.getUseFile()) {
                    throw new EmptyInputException();}
                return password;
                } catch (EmptyInputException exception) {
                console.printErr("имя не может быть пустым");
            }
        }
    }
}
