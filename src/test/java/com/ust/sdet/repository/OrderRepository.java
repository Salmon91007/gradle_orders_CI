package com.ust.sdet.repository;

import com.ust.sdet.model.OrderRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    private final Connection connection;

    public OrderRepository() {

        try {
            connection = DriverManager.getConnection(
                    System.getProperty("db.url", "jdbc:postgresql://localhost:5432/testdb"),
                    System.getProperty("db.user", "postgres"),
                    System.getProperty("db.password", "postgres")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(OrderRecord order) throws SQLException {

        String sql = """
                INSERT INTO orders
                (sku, quantity, total_paise, status, ordered_on, refunded)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, order.sku());
            ps.setInt(2, order.quantity());
            ps.setLong(3, order.totalPaise());
            ps.setString(4, order.status());
            ps.setDate(5, Date.valueOf(order.orderedOn()));
            ps.setBoolean(6, order.refunded());

            ps.executeUpdate();
        }
    }

    public Optional<OrderRecord> findBySku(String sku) throws SQLException {

        String sql = "SELECT * FROM orders WHERE sku = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, sku);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<OrderRecord> findAll() throws SQLException {

        List<OrderRecord> orders = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) {

            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        }

        return orders;
    }

    public long count() throws SQLException {

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM orders")) {

            rs.next();
            return rs.getLong(1);
        }
    }

    public void reset() throws SQLException {

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM orders");
        }
    }

    public void deleteBySku(String sku) throws SQLException {

        try (PreparedStatement ps =
                     connection.prepareStatement("DELETE FROM orders WHERE sku = ?")) {

            ps.setString(1, sku);
            ps.executeUpdate();
        }
    }

    private OrderRecord mapRow(ResultSet rs) throws SQLException {

        return new OrderRecord(
                rs.getString("sku"),
                rs.getInt("quantity"),
                rs.getLong("total_paise"),
                rs.getString("status"),
                rs.getDate("ordered_on").toLocalDate(),
                rs.getBoolean("refunded")
        );
    }
}