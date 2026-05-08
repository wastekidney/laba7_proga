package ru.itmo.client.CollectionForm;

import ru.itmo.client.MainClient;
import ru.itmo.common.Collection.Address;
import ru.itmo.client.Console.Console;
import ru.itmo.client.InputFromFile.ScannerFile;
import ru.itmo.common.Exeption.PartNumberFormatException;

import java.util.NoSuchElementException;

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
                MainClient.logger.info("адрес не может быть пустым" + e.getMessage());
            } catch (NoSuchElementException e) {
                MainClient.logger.info("адрес не распознан" + e.getMessage());
            }
        }
        return new Address(officialAddress);
    }
}
