package ru.itmo.server.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC драйвер не найден", e);
        }
    }

    public static void configure(String url, String user, String password) {
        DatabaseHandler.url = url;
        DatabaseHandler.user = user;
        DatabaseHandler.password = password;
    }

    public static Connection getConnection() throws SQLException {
        if (url == null) {
            throw new IllegalStateException("База данных не настроена. Сначала вызовите метод configure()");
        }
        return DriverManager.getConnection(url, user, password);
    }
}