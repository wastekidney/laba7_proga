package ru.itmo.client.Commands;

import ru.itmo.client.networkUDP.UDPClient;
import ru.itmo.common.Collection.UnitOfMeasure;
import ru.itmo.client.Console.Console;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.common.network.request.PrintFieldDescendingUnitOfMeasureRequest;
import ru.itmo.common.network.response.PrintFieldDescendingUnitOfMeasureResponse;

public class PrintFieldDescendingUnitOfMeasure extends Command{
    private final Console console;
    private final UDPClient udpClient;
    public PrintFieldDescendingUnitOfMeasure(Console console, UDPClient udpClient) {
        super("print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания");
        this.console = console;
        this.udpClient = udpClient;
    }

    @Override
    public void execute(String element) {
        var response = (PrintFieldDescendingUnitOfMeasureResponse)  udpClient.sendReceiveMessage(new PrintFieldDescendingUnitOfMeasureRequest());
        console.println(response.getMessage());
    }
}
