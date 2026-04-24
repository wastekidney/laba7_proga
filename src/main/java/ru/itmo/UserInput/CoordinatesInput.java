package ru.itmo.UserInput;

import ru.itmo.Collection.Coordinates;
import ru.itmo.Console.Console;
import ru.itmo.UserInput.InputFromFile.ScannerFile;
import ru.itmo.utils.CoordinateFormatException;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.PartNumberFormatException;

import java.util.Objects;
import java.util.Scanner;

public class CoordinatesInput {
    private final Console console;

    public CoordinatesInput(Console console) {
        this.console = console;
    }

    public Coordinates askCoordinates(){
        Double x;
        float y;
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите координаты x y: ");
                }
                var coordinates = ScannerFile.getScanner().nextLine().trim().split(" ", 2);
                if (coordinates.length == 0) {throw new EmptyInputException();
                }
                if (Objects.equals(coordinates[0], coordinates.toString())) throw new CoordinateFormatException();
                x = Double.parseDouble(coordinates[0]);
                y = Float.parseFloat(coordinates[1]);
                break;

            } catch (EmptyInputException e) {
                console.printErr("вы ничего не ввели");
            } catch (NumberFormatException e) {
                console.printErr("вы неверно ввели данные");
            } catch (CoordinateFormatException e) {
                console.printErr("неправильно ввели координаты");
            }

        }
        return new Coordinates(x, y);
    }
}
