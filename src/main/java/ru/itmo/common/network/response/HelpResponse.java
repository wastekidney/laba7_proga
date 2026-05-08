package ru.itmo.common.network.response;

public class HelpResponse extends Response {
    private final String message;
    public HelpResponse(String message,  String messageError) {
        super("help",  messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
