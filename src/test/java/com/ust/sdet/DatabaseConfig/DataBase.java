package com.ust.sdet.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {

    private DataBase() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getProperty("db.url", "jdbc:postgresql://localhost:5432/orderdb"),
                System.getProperty("db.user", "salman"),
                System.getProperty("db.password", "Salmon@123")
        );
    }
}