package com.ust.sdet.builder;

import com.ust.sdet.model.OrderRecord;

import java.time.LocalDate;
import java.util.UUID;

public class OrderBuilder {

    private String sku = "SKU";
    private int quantity = 1;
    private long totalPaise = 1299_00;
    private String status = "NEW";
    private LocalDate orderedOn = LocalDate.now();
    private boolean refunded = false;

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder withTotalPaise(long totalPaise) {
        this.totalPaise = totalPaise;
        return this;
    }

    public OrderBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder withOrderedOn(LocalDate orderedOn) {
        this.orderedOn = orderedOn;
        return this;
    }

    public OrderBuilder refunded(boolean refunded) {
        this.refunded = refunded;
        return this;
    }

    public OrderRecord build() {
        return new OrderRecord(
                sku,
                quantity,
                totalPaise,
                status,
                orderedOn,
                refunded
        );
    }
}