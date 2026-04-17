package ru.itmo.UserInput;

import ru.itmo.Collection.Organization;
import ru.itmo.Collection.OrganizationType;
import ru.itmo.Console.Console;

import java.util.Scanner;
import ru.itmo.UserInput.InputFromFile.ScannerFile;
import ru.itmo.utils.EmptyInputException;
import ru.itmo.utils.NameFormatException;

public class OrganizationInput {
    private final Console console;

    public OrganizationInput(Console console) {
        this.console = console;
    }

    public Organization askOrganization() {
        String name = null;
        Double annualTurnover = null;
        try {
            name = askName();
            annualTurnover = askAnnualTurnover();
        } catch (Exception e) {
            console.print("seer");
        }
        return new Organization(name, annualTurnover, askOrganizationType(), new AdressInput(console).askAdress());
    }

    public String askName() {
        String name;
        while (true) {
            try {
                console.print("введите имя организации");
                name = ScannerFile.getScanner().nextLine(); //here
                if (name.isEmpty()) throw new EmptyInputException();
                if (name.matches(".*\\d.*")) throw new NameFormatException();
                break;
            } catch (EmptyInputException e) {
                console.print("имя не может быть пустым");
            } catch (NameFormatException e) {
                console.print("в типе содержатся цифры");
            }
        }
        return name;
    }


    public Double askAnnualTurnover() {
        Double annualTurnover;
        while (true) {
            try {
                console.print("введите годовой оборот организации");
                String annualTurnoverString = ScannerFile.getScanner().nextLine(); // here
                if (annualTurnoverString.isEmpty()) {throw new EmptyInputException();
                }
                annualTurnover = Double.parseDouble(annualTurnoverString);
                break;
            } catch (EmptyInputException e) {
                console.print("Годовой оборот не может быть пустым");
            } catch (NumberFormatException e) {
                console.print("не соответвует числу");
            }
        }
        return annualTurnover;
    }

    public OrganizationType askOrganizationType() {
        String organizationType;
        while (true) {
            try {
                console.print("введите тип организации");
                console.print(OrganizationType.names());
                organizationType = ScannerFile.getScanner().nextLine().toUpperCase();
                if (organizationType.isEmpty()) {
                    throw new EmptyInputException();
                }
                if (organizationType.matches(".*\\d.*")) throw new NameFormatException();
                break;
            } catch (EmptyInputException e) {
                console.print("тип не может быть пустым");
            } catch (NameFormatException e) {
                console.print("в типе содержатся цифры");
            }
        }
        return OrganizationType.valueOf(organizationType.toUpperCase());
    }

}
