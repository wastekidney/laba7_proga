package ru.itmo.client.collectionForm;

import ru.itmo.client.MainClient;
import ru.itmo.common.Collection.Coordinates;
import ru.itmo.client.Console.Console;
import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Exeption.CoordinateFormatException;
import ru.itmo.common.Exeption.EmptyInputException;

import java.util.Objects;

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
                MainClient.logger.info("вы ничего не ввели" + e.getMessage());
            } catch (NumberFormatException e) {
                console.printErr("вы неверно ввели данные");
                MainClient.logger.info("вы неверно ввели данные" + e.getMessage());
            } catch (CoordinateFormatException e) {
                console.printErr("неправильно ввели координаты");
                MainClient.logger.info("неправильно ввели координаты" + e.getMessage());
            }

        }
        return new Coordinates(x, y);
    }
}
