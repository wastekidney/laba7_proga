package ru.itmo.common.network.response;

public class AuthenticationResponse extends Response {
    private final String message;
    public AuthenticationResponse(String message, String error) {
        super("authentication", error);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}