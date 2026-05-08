package ru.itmo.common.network.response;

public class AddResponse extends Response {
    private final String message;
    public AddResponse(String message, String messageError) {
        super("add", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
