package ru.itmo.Commands;

import ru.itmo.Collection.UnitOfMeasure;
import ru.itmo.Console.Console;
import ru.itmo.UserInput.ProductInput;
import ru.itmo.utils.ElementException;

public class PrintFieldDescendingUnitOfMeasure extends Command{
    private final Console console;
    public PrintFieldDescendingUnitOfMeasure(Console console) {
        super("print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания");
        this.console = console;
    }

    @Override
    public void execute(String element) {
        try {
            if (element != null) throw new ElementException();
            console.println(UnitOfMeasure.nameReserved());
        } catch (ElementException e) {
            console.printErr("в этой команде не должны быть элементы");
        }

    }
}
