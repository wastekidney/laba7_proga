package ru.itmo.client.InputFromFile;

import java.util.Scanner;

public class ScannerFile {
    private static Scanner scanner;
    private static boolean useFile = false;

    public static Scanner getScanner() {
        return scanner;
    }
    public static void setScanner(Scanner scanner) {
        ScannerFile.scanner = scanner;
    }
    public static boolean getUseFile() {
        return useFile;
    }
    public static void setUseFile() {
        ScannerFile.useFile = true;
    }
    public static void setNoUseFile() {
        ScannerFile.useFile = false;
    }
}
