package ru.itmo.server.Commands;

import ru.itmo.common.network.request.Request;
import ru.itmo.common.network.response.PrintFieldDescendingUnitOfMeasureResponse;
import ru.itmo.common.Collection.UnitOfMeasure;
import ru.itmo.common.Exeption.ElementException;
import ru.itmo.server.MainServer;

public class PrintFieldDescendingUnitOfMeasure extends Command {
    public PrintFieldDescendingUnitOfMeasure() {
        super("print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания");
    }

    @Override
    public PrintFieldDescendingUnitOfMeasureResponse execute(Request request) {
        StringBuilder sbError = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(UnitOfMeasure.nameReserved());
        } catch (Exception e) {
            String messageError = "ошибка:" + e.getMessage();
            MainServer.logger.info(messageError);
            sbError.append(messageError);
        }
        return new PrintFieldDescendingUnitOfMeasureResponse(sb.toString(), sbError.toString());
    }
}
