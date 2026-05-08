package ru.itmo.common.network.response;

public class RemoveLowerResponse extends Response {
    private final String message;
    public RemoveLowerResponse(String message, String messageError) {
        super("remove_lower", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
