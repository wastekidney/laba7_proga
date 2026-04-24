package ru.itmo.UserInput;

import ru.itmo.Collection.Address;
import ru.itmo.Console.Console;
import ru.itmo.UserInput.InputFromFile.ScannerFile;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.PartNumberFormatException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class AdressInput {
    private final Console console;

    public AdressInput(Console console) {
        this.console = console;
    }

    public Address askAdress(){
        String officialAddress;
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите officialAddress");
                }
                officialAddress = ScannerFile.getScanner().nextLine().trim();
                if ((officialAddress.isEmpty() || officialAddress == null) && !ScannerFile.getUseFile()) {throw new PartNumberFormatException();
                }
                break;
            } catch (PartNumberFormatException e) {
                console.print("адрес не может быть пустым");
            } catch (NoSuchElementException e) {
                console.print("адрес не распознан");
            }
        }
        return new Address(officialAddress);
    }
}
