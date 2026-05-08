package ru.itmo.common.network.response;

public class RemoveByIdResponse extends Response {
    private final String message;
    public RemoveByIdResponse(String message, String messageError) {
        super("remove_by_id", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
