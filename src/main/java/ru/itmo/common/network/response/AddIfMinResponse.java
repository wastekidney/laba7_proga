package ru.itmo.common.network.response;

public class AddIfMinResponse extends Response{
    private final String message;
    public AddIfMinResponse(String message, String messageError) {
        super(message, messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
