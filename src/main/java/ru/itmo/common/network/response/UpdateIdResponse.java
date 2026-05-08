package ru.itmo.common.network.response;

import ru.itmo.common.Collection.Product;

public class UpdateIdResponse extends Response {
    private final String message;
    public UpdateIdResponse(String message, String messageError) {
        super("update_id", messageError);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

