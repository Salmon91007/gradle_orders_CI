package com.ust.sdet.factory;

import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.repository.OrderRepository;

import java.sql.SQLException;

public class OrderFactory {

    private final OrderRepository repository;

    public OrderFactory(OrderRepository repository) {
        this.repository = repository;
    }

    public void persisted(OrderBuilder builder) throws SQLException {
        repository.save(builder.build());
    }
}