package com.ust.sdet.model;

import java.time.LocalDate;

public record OrderRecord(
        String sku,
        int quantity,
        long totalPaise,
        String status,
        LocalDate orderedOn,
        boolean refunded
) {
}