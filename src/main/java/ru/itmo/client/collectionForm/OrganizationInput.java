package ru.itmo.client.collectionForm;

import ru.itmo.client.MainClient;
import ru.itmo.common.Collection.Organization;
import ru.itmo.common.Collection.OrganizationType;
import ru.itmo.client.Console.Console;

import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Exeption.EmptyInputException;
import ru.itmo.common.Exeption.NameFormatException;

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
            console.print("");
        }
        return new Organization(name, annualTurnover, askOrganizationType(), new AdressInput(console).askAdress());
    }

    public String askName() {
        String name;
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {console.print("введите имя организации");}
                name = ScannerFile.getScanner().nextLine(); //here
                if (name.isEmpty() && !ScannerFile.getUseFile()) throw new EmptyInputException();
                if (name.matches(".*\\d.*") && !ScannerFile.getUseFile()) throw new NameFormatException();
                break;
            } catch (EmptyInputException e) {
                console.printErr("имя не может быть пустым");
                MainClient.logger.info("имя не может быть пустым" + e.getMessage());
            } catch (NameFormatException e) {
                console.printErr("в типе содержатся цифры");
                MainClient.logger.info("в типе содержатся цифры" + e.getMessage());
            }
        }
        return name;
    }


    public Double askAnnualTurnover() {
        Double annualTurnover;
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {console.print("введите годовой оборот организации");}
                String annualTurnoverString = ScannerFile.getScanner().nextLine(); // here
                if (annualTurnoverString.isEmpty() && !ScannerFile.getUseFile()) {throw new EmptyInputException();
                }
                annualTurnover = Double.parseDouble(annualTurnoverString);
                break;
            } catch (EmptyInputException e) {
                console.printErr("Годовой оборот не может быть пустым");
                MainClient.logger.info("Годовой оборот не может быть пустым" + e.getMessage());
            } catch (NumberFormatException e) {
                console.printErr("не соответвует числу");
                MainClient.logger.info("не соответвует числу" + e.getMessage());


            }
        }
        return annualTurnover;
    }

    public OrganizationType askOrganizationType() {
        String organizationType;
        while (true) {
            try {
                if (!ScannerFile.getUseFile()) {
                console.print("введите тип организации");
                console.print(OrganizationType.names());
                }
                organizationType = ScannerFile.getScanner().nextLine().toUpperCase();
                if (organizationType.isEmpty() && !ScannerFile.getUseFile()) {
                    throw new EmptyInputException();
                }
                if (organizationType.matches(".*\\d.*")) throw new NameFormatException();
                break;
            } catch (EmptyInputException e) {
                console.printErr("тип не может быть пустым");
                MainClient.logger.info("тип не может быть пустым"  + e.getMessage());

            } catch (NameFormatException e) {
                console.printErr("в типе содержатся цифры");
                MainClient.logger.info("в типе содержатся цифры"  + e.getMessage());
            }
        }
        return OrganizationType.valueOf(organizationType.toUpperCase());
    }

}
