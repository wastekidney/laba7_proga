package ru.itmo.client.CollectionForm;

import ru.itmo.client.MainClient;
import ru.itmo.common.Collection.Coordinates;
import ru.itmo.common.Collection.Organization;
import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.UnitOfMeasure;
import ru.itmo.client.Console.Console;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.common.Exeption.NameFormatException;
import ru.itmo.common.Exeption.PartNumberFormatException;


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
                MainClient.logger.info("элемента не существует" + exception.getMessage());
            } catch (IllegalStateException exception) {
                console.printErr("недопустимый ввод");
                MainClient.logger.info("недопустимый ввод" + exception.getMessage());
            } catch (EmptyInputException exception) {
                console.printErr("имя не может быть пустым");
                MainClient.logger.info("имя не может быть пустым" + exception.getMessage());
            } catch (NameFormatException exception) {
                console.printErr("в имени содержатся цифры");
                MainClient.logger.info("в имени содержатся цифры");
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
            console.printErr("можно вводить только цифры");
            MainClient.logger.info("можно вводить только цифры");
        } catch (PartNumberFormatException e) {
            console.printErr("цена меньше 0");
            MainClient.logger.info("цена меньше 0");
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
            console.printErr("длина строки должна быть в диапазоне [27, 66]");
            MainClient.logger.info("длина строки должна быть в диапазоне [27, 66]");

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
            } catch (NumberFormatException exception) {
                console.printErr("можно вводить только цифры");
                MainClient.logger.info("можно вводить только цифры");
            } catch (EmptyInputException e) {
                console.printErr("пустая строка");
                MainClient.logger.info("пустая строка");
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
