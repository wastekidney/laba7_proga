package ru.itmo.UserInput;

import ru.itmo.Collection.UnitOfMeasure;
import ru.itmo.Console.Console;
import ru.itmo.UserInput.InputFromFile.ScannerFile;
import ru.itmo.utils.EmptyInputException;

import java.util.Scanner;

public class UnitOfMeasureInput {
    private final Console console;

    public UnitOfMeasureInput(Console console) {
        this.console = console;
    }
    public UnitOfMeasure askUnitOfMeasure(){
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите unitOfMeasure" + " " + UnitOfMeasure.names());
                }
                String OneOfUnitOfMeasure = ScannerFile.getScanner().nextLine();
                if (OneOfUnitOfMeasure.isEmpty()) {throw new EmptyInputException();
                }
                return UnitOfMeasure.valueOf(OneOfUnitOfMeasure.toUpperCase());
            } catch (EmptyInputException e) {
                console.print("unitOfMeasure не может быть пустым");
            } catch (IllegalArgumentException e) {
                console.print("unitOfMeasure нет в enum");
            }
        }
    }
}
