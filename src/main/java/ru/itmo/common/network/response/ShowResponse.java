package ru.itmo.common.network.response;

public class ShowResponse extends Response {
    private final String message;
    public ShowResponse(String message, String messageError) {
        super("show", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
