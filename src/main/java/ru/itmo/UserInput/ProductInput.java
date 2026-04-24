package ru.itmo.UserInput;

import ru.itmo.Collection.Coordinates;
import ru.itmo.Collection.Organization;
import ru.itmo.Collection.Product;
import ru.itmo.Collection.UnitOfMeasure;
import ru.itmo.Console.Console;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import ru.itmo.UserInput.InputFromFile.ScannerFile;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.NameFormatException;
import ru.itmo.utils.PartNumberFormatException;


public class ProductInput {
    private final Console console;

    public ProductInput(Console console) {
        this.console = console;
    }
    public Product askProduct(){
        Product product = new Product(askProductName(), askCoordinates(),
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")), askPrice(), askPartNumber(),
                askManufactureCost(), askUnitOfMeasure(), askOrganization());
        return product;
    }

    private String askProductName(){
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите названия продукта:");
                }
                String productName = ScannerFile.getScanner().nextLine(); // here
                if (productName.isEmpty() && !ScannerFile.getUseFile()) {
                    throw new EmptyInputException();
                }
                if (productName.matches(".*\\d.*")) throw new NameFormatException();
                return productName;
            } catch (NoSuchElementException exception)  {
                console.printErr("элемента не существует");
            } catch (IllegalStateException exception) {
                console.printErr("недопустимый ввод");
            } catch (EmptyInputException exception) {
                console.printErr("имя не может быть пустым");
            } catch (NameFormatException exception) {
                console.printErr("в имени содержатся цифры");
            }

        }
    }

    private Coordinates askCoordinates(){
        return new CoordinatesInput(console).askCoordinates();
    }

    private float askPrice() {
        while (true) {
        try {
            if (!ScannerFile.getUseFile()) {
                console.print("введите цену");
            }
            float price = Float.parseFloat(ScannerFile.getScanner().nextLine());
            if (price < 0 && !ScannerFile.getUseFile()) {throw new PartNumberFormatException();
            }
            return price;
        } catch (NumberFormatException exception) {
            console.print("не вводите фигню");
        } catch (PartNumberFormatException e) {
            console.print("цена меньше 0");
        }
        }

    }

    private String askPartNumber() {
        while (true) {
        try {
            if (!ScannerFile.getUseFile()) {
            console.print("введите partNumber");
            }
            String partNumber = ScannerFile.getScanner().nextLine();
            if (partNumber.length() < 26 || partNumber.length() > 67) throw new PartNumberFormatException();
            return partNumber;

        } catch (PartNumberFormatException exception) {
            console.print("длина строки должна быть в диапазоне [27, 66]");
        }
        }
    }

    private double askManufactureCost() {
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите manufactureCost");
                }
                String manufactureCostStr = ScannerFile.getScanner().nextLine();
                if (manufactureCostStr.isEmpty()) throw new EmptyInputException();
                double manufactureCost = Double.parseDouble(manufactureCostStr);
                return manufactureCost;
            } catch (PartNumberFormatException exception) {

            } catch (EmptyInputException e) {
                console.print("пустая строка");
            }
        }
    }
    private UnitOfMeasure askUnitOfMeasure() {
        return new UnitOfMeasureInput(console).askUnitOfMeasure();
    }
    private Organization askOrganization() {
        return new OrganizationInput(console).askOrganization();
    }

}
