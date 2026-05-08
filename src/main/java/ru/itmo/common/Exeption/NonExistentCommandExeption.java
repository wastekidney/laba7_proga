package ru.itmo.common.Exeption;

public class NonExistentCommandExeption extends RuntimeException {
    public NonExistentCommandExeption(String message) {
        super(message);
    }
}
