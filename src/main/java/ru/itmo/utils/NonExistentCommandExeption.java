package ru.itmo.utils;

public class NonExistentCommandExeption extends RuntimeException {
    public NonExistentCommandExeption(String message) {
        super(message);
    }
}
