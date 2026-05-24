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

        try (
                Connection conn = DatabaseHandler.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {
                products.add(map(rs));
            }
        }

        return products;
    }

    public static Product add(Product product, int userId)
            throws SQLException {

        String sql = """
            INSERT INTO product
            (
                name,
                x,
                y,
                price,
                part_number,
                manufacture_cost,
                unit_of_measure,
                organization_name,
                annual_turnover,
                organization_type,
                street,
                user_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id, creation_date
        """;

        try (
                Connection conn = DatabaseHandler.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getName());

            stmt.setDouble(2,
                    product.getCoordinates().getX());

            stmt.setFloat(3,
                    product.getCoordinates().getY());

            stmt.setFloat(4,
                    product.getPrice());

            stmt.setString(5,
                    product.getPartNumber());

            if (product.getManufactureCost() != null) {
                stmt.setDouble(6,
                        product.getManufactureCost());
            } else {
                stmt.setNull(6, Types.DOUBLE);
            }

            if (product.getUnitOfMeasure() != null) {
                stmt.setString(7,
                        product.getUnitOfMeasure().name());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            stmt.setString(8,
                    product.getManufacturer().getName());

            if (product.getManufacturer()
                    .getAnnualTurnover() != null) {

                stmt.setDouble(9,
                        product.getManufacturer()
                                .getAnnualTurnover());

            } else {
                stmt.setNull(9, Types.DOUBLE);
            }

            if (product.getManufacturer().getType() != null) {

                stmt.setString(10,
                        product.getManufacturer()
                                .getType().name());

            } else {
                stmt.setNull(10, Types.VARCHAR);
            }

            stmt.setString(11,
                    product.getManufacturer()
                            .getOfficialAddress()
                            .getStreet());

            stmt.setInt(12, userId);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            product.setId(rs.getLong("id"));

            Timestamp ts =
                    rs.getTimestamp("creation_date");

            product.setCreationDate(
                    ZonedDateTime.ofInstant(
                            ts.toInstant(),
                            ZoneId.systemDefault()
                    )
            );

            product.setUserId(userId);

            return product;
        }
    }

    public static void delete(long id, int userId)
            throws SQLException {

        String sql = """
            DELETE FROM product
            WHERE id = ? AND user_id = ?
        """;

        try (
                Connection conn = DatabaseHandler.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);
            stmt.setInt(2, userId);

            int affected = stmt.executeUpdate();

            if (affected == 0) {
                throw new SQLException(
                        "Объект не найден или не принадлежит вам"
                );
            }
        }
    }

    public static void update(
            long id,
            Product product,
            int userId
    ) throws SQLException {

        String sql = """
            UPDATE product
            SET
                name = ?,
                x = ?,
                y = ?,
                price = ?,
                part_number = ?,
                manufacture_cost = ?,
                unit_of_measure = ?,
                organization_name = ?,
                annual_turnover = ?,
                organization_type = ?,
                street = ?
            WHERE id = ? AND user_id = ?
        """;

        try (
                Connection conn = DatabaseHandler.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getName());

            stmt.setDouble(2,
                    product.getCoordinates().getX());

            stmt.setFloat(3,
                    product.getCoordinates().getY());

            stmt.setFloat(4,
                    product.getPrice());

            stmt.setString(5,
                    product.getPartNumber());

            if (product.getManufactureCost() != null) {
                stmt.setDouble(6,
                        product.getManufactureCost());
            } else {
                stmt.setNull(6, Types.DOUBLE);
            }

            if (product.getUnitOfMeasure() != null) {
                stmt.setString(7,
                        product.getUnitOfMeasure().name());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            stmt.setString(8,
                    product.getManufacturer().getName());

            if (product.getManufacturer()
                    .getAnnualTurnover() != null) {

                stmt.setDouble(9,
                        product.getManufacturer()
                                .getAnnualTurnover());

            } else {
                stmt.setNull(9, Types.DOUBLE);
            }

            if (product.getManufacturer().getType() != null) {

                stmt.setString(10,
                        product.getManufacturer()
                                .getType().name());

            } else {
                stmt.setNull(10, Types.VARCHAR);
            }

            stmt.setString(11,
                    product.getManufacturer()
                            .getOfficialAddress()
                            .getStreet());

            stmt.setLong(12, id);

            stmt.setInt(13, userId);

            int affected = stmt.executeUpdate();

            if (affected == 0) {
                throw new SQLException(
                        "Объект не найден или не принадлежит вам"
                );
            }
        }
    }

    private static Product map(ResultSet rs)
            throws SQLException {

        Address address = new Address();
        address.setStreet(rs.getString("street"));

        Organization organization =
                new Organization();

        organization.setName(
                rs.getString("organization_name"));

        double turnover =
                rs.getDouble("annual_turnover");

        if (!rs.wasNull()) {
            organization.setAnnualTurnover(turnover);
        }

        String type =
                rs.getString("organization_type");

        if (type != null) {
            organization.setType(
                    OrganizationType.valueOf(type)
            );
        }

        organization.setOfficialAddress(address);

        Coordinates coordinates =
                new Coordinates();

        coordinates.setX(rs.getDouble("x"));
        coordinates.setY(rs.getFloat("y"));

        Product product = new Product();

        product.setId(rs.getLong("id"));

        product.setName(rs.getString("name"));

        product.setCoordinates(coordinates);

        Timestamp ts =
                rs.getTimestamp("creation_date");

        if (ts != null) {

            product.setCreationDate(
                    ZonedDateTime.ofInstant(
                            ts.toInstant(),
                            ZoneId.systemDefault()
                    )
            );
        }

        product.setPrice(rs.getFloat("price"));

        product.setPartNumber(
                rs.getString("part_number"));

        double manufactureCost =
                rs.getDouble("manufacture_cost");

        if (!rs.wasNull()) {
            product.setManufactureCost(
                    manufactureCost
            );
        }

        String uom =
                rs.getString("unit_of_measure");

        if (uom != null) {
            product.setUnitOfMeasure(
                    UnitOfMeasure.valueOf(uom)
            );
        }

        product.setManufacturer(organization);

        product.setUserId(rs.getInt("user_id"));

        return product;
    }
}