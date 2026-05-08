package ru.itmo.common.network.response;

public class PrintAscendingResponse extends Response {
    private final String message;
    public PrintAscendingResponse(String message, String messageError) {
        super("print_ascending", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
