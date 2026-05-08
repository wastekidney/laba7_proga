package ru.itmo.common.network.response;

import java.io.Serializable;

public class Response implements Serializable {
    private final String message;
    private final String messageError;

    public Response(String message, String messageError) {
        this.message = message;
        this.messageError = messageError;
    }
    public String getName() {
        return message;
    }
    public String getMessageError() {
        return messageError;
    }

}
