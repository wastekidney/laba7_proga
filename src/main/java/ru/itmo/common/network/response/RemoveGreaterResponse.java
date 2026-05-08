package ru.itmo.common.network.response;

public class RemoveGreaterResponse extends Response {
    private  String message;
    public RemoveGreaterResponse(String message, String messageError) {
        super("remove_greater", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
