package com.ust.sdet.tests;

import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDataIT {

    private static final OrderRepository repo = new OrderRepository();
    private static final OrderFactory fact = new OrderFactory(repo);

    @BeforeAll
    static void migrateDatabase() {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getProperty("db.url", "jdbc:postgresql://localhost:5432/orderdb"),
                        System.getProperty("db.user", "salman"),
                        System.getProperty("db.password", "Salmon@123")
                )
                .load();

        flyway.migrate();
    }

    @BeforeEach
    void setup() throws SQLException {
        repo.reset();
    }

    @Test
    void creates() throws SQLException {

        fact.persisted(
                OrderBuilder.anOrder()
                        .withQuantity(3)
        );
    }

    @Test
    void counts() throws SQLException {

        fact.persisted(
                OrderBuilder.anOrder()
        );

        assertEquals(1, repo.count());
    }
}