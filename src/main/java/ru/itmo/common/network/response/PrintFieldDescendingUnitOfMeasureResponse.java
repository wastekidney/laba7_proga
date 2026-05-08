package ru.itmo.common.network.response;

public class PrintFieldDescendingUnitOfMeasureResponse extends Response{
    private final String message;
    public PrintFieldDescendingUnitOfMeasureResponse(String message, String messageError) {
        super("print_field_descending_unit_of_measure", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
