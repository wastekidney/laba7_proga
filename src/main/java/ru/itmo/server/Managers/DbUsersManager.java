package ru.itmo.server.Managers;

import ru.itmo.common.Collection.User.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUsersManager {

    public static User registerUser(Connection connection, User user) throws SQLException {
        String checkSql = "SELECT id FROM users WHERE login = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, user.getName());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                throw new SQLException("пользователь с логином '" + user.getName() + "' уже создан");
            }
        }

        String insertSql = "INSERT INTO users (login, password_hash) VALUES (?, ?) RETURNING id";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            insertStmt.setString(1, user.getName());
            insertStmt.setString(2, hashPassword(user.getPassword()));
            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                return new User(id, user.getName(), null);
            }
        }
        throw new SQLException("ошибка регистрации пользователя");
    }

    public static User loginUser(Connection connection, User user) throws SQLException {
        String sql = "SELECT id, password_hash FROM users WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (storedHash.equals(hashPassword(user.getPassword()))) {
                    int id = rs.getInt("id");
                    return new User(id, user.getName(), null);
                }
            }
        }
        return null;
    }

    public static User authenticateUser(Connection connection, User user) throws SQLException {
        String sql = "SELECT id, password_hash FROM users WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (storedHash.equals(hashPassword(user.getPassword()))) {
                    int id = rs.getInt("id");
                    return new User(id, user.getName(), null);
                }
            }
        }
        throw new SQLException("Неверный логин или пароль");
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}