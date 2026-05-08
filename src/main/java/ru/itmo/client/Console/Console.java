package ru.itmo.client.Console;

public class Console implements ConsoleInteface{

    @Override
    public void print(String message) {
        System.out.print(message + " ");
    }

    @Override
    public void printErr(String message) {
        System.out.println("\u001B[36m" + "ошибка:" + message + "\u001B[0m");
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

}
