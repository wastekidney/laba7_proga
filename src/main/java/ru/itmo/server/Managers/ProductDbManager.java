package ru.itmo.server.Managers;

import ru.itmo.common.Collection.*;
import ru.itmo.server.handlers.DatabaseHandler;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDbManager {

    public static List<Product> loadAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Connection conn = DatabaseHandler.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(map(rs));
            }
        }
        return products;
    }

    public static Product add(Product product, int userId) throws SQLException {
        String sql = """
            INSERT INTO product (name, x, y, price, part_number,
                                 manufacture_cost, unit_of_measure,
                                 organization_name, annual_turnover,
                                 organization_type, street, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id, creation_date
        """;
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getCoordinates().getX());
            stmt.setFloat(3, product.getCoordinates().getY());
            stmt.setFloat(4, product.getPrice());
            stmt.setString(5, product.getPartNumber());

            if (product.getManufactureCost() >= 0)
                stmt.setDouble(6, product.getManufactureCost());
            else
                stmt.setNull(6, Types.DOUBLE);
            if (product.getUnitOfMeasure() != null)
                stmt.setString(7, product.getUnitOfMeasure().name());
            else
                stmt.setNull(7, Types.VARCHAR);
            stmt.setString(8, product.getManufacturer().getName());
            if (product.getManufacturer().getAnnualTurnover() != null)
                stmt.setDouble(9, product.getManufacturer().getAnnualTurnover());
            else
                stmt.setNull(9, Types.DOUBLE);

            if (product.getManufacturer().getType() != null)
                stmt.setString(10, product.getManufacturer().getType().name());
            else
                stmt.setNull(10, Types.VARCHAR);

            stmt.setString(11, product.getManufacturer().getOfficialAddress().getStreet());
            stmt.setInt(12, userId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            long generatedId = rs.getLong("id");
            Timestamp ts = rs.getTimestamp("creation_date");
            ZonedDateTime creationDate = ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());

            product.setId(generatedId);
            product.setCreationDate(creationDate);
            product.setUserId(userId);
            return product;
        }
    }

    public static boolean deleteById(long id, int userId) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setInt(2, userId);
            int affected = stmt.executeUpdate();
            return affected > 0;
        }
    }

    public static boolean update(long id, Product product, int userId) throws SQLException {
        String sql = """
            UPDATE product SET name=?, x=?, y=?, price=?, part_number=?,
            manufacture_cost=?, unit_of_measure=?, organization_name=?,
            annual_turnover=?, organization_type=?, street=?
            WHERE id=? AND user_id=?
        """;
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getCoordinates().getX());
            stmt.setFloat(3, product.getCoordinates().getY());
            stmt.setFloat(4, product.getPrice());
            stmt.setString(5, product.getPartNumber());

            if (product.getManufactureCost() >= 0 && product.getManufactureCost() >= 0)
                stmt.setDouble(6, product.getManufactureCost());
            else
                stmt.setNull(6, Types.DOUBLE);

            if (product.getUnitOfMeasure() != null)
                stmt.setString(7, product.getUnitOfMeasure().name());
            else
                stmt.setNull(7, Types.VARCHAR);

            stmt.setString(8, product.getManufacturer().getName());
            if (product.getManufacturer().getAnnualTurnover() != null)
                stmt.setDouble(9, product.getManufacturer().getAnnualTurnover());
            else
                stmt.setNull(9, Types.DOUBLE);

            if (product.getManufacturer().getType() != null)
                stmt.setString(10, product.getManufacturer().getType().name());
            else
                stmt.setNull(10, Types.VARCHAR);

            stmt.setString(11, product.getManufacturer().getOfficialAddress().getStreet());
            stmt.setLong(12, id);
            stmt.setInt(13, userId);

            int updated = stmt.executeUpdate();
            return updated > 0;
        }
    }

    public static int clearByUser(int userId) throws SQLException {
        String sql = "DELETE FROM product WHERE user_id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate();
        }
    }

    public static int removeGreaterByUser(float price, int userId) throws SQLException {
        String sql = "DELETE FROM product WHERE price > ? AND user_id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, price);
            stmt.setInt(2, userId);
            return stmt.executeUpdate();
        }
    }

    public static int removeLowerByUser(float price, int userId) throws SQLException {
        String sql = "DELETE FROM product WHERE price < ? AND user_id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, price);
            stmt.setInt(2, userId);
            return stmt.executeUpdate();
        }
    }

    private static Product map(ResultSet rs) throws SQLException {
        Address address = new Address(rs.getString("street"));
        Double annualTurnover = rs.getDouble("annual_turnover");
        if (rs.wasNull()) annualTurnover = null;

        String orgTypeStr = rs.getString("organization_type");
        OrganizationType orgType = orgTypeStr != null ? OrganizationType.valueOf(orgTypeStr) : null;

        Organization organization = new Organization(
                rs.getString("organization_name"),
                annualTurnover,
                orgType,
                address
        );

        Coordinates coordinates = new Coordinates(rs.getDouble("x"), rs.getFloat("y"));

        Timestamp ts = rs.getTimestamp("creation_date");
        ZonedDateTime creationDate = null;
        if (ts != null)
            creationDate = ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());

        Double manufactureCost = rs.getDouble("manufacture_cost");
        if (rs.wasNull()) manufactureCost = null;

        String uomStr = rs.getString("unit_of_measure");
        UnitOfMeasure uom = uomStr != null ? UnitOfMeasure.valueOf(uomStr) : null;

        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                coordinates,
                creationDate,
                rs.getFloat("price"),
                rs.getString("part_number"),
                manufactureCost,
                uom,
                organization,
                rs.getInt("user_id")
        );
    }
}