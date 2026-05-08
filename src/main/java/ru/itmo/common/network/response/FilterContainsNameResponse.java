package ru.itmo.common.network.response;

public class FilterContainsNameResponse extends Response {
    private final String message;
    public FilterContainsNameResponse(String message, String messageError) {
        super("filter_contains_name", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
