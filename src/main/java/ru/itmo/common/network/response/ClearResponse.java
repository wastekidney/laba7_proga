package ru.itmo.common.network.response;

public class ClearResponse extends Response {
    private final String message;
    public ClearResponse(String message, String messageError) {
        super("clear",  messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
