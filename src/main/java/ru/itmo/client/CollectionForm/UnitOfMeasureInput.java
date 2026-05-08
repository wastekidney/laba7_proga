package ru.itmo.client.CollectionForm;

import ru.itmo.common.Collection.UnitOfMeasure;
import ru.itmo.client.Console.Console;
import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Exeption.EmptyInputException;

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
