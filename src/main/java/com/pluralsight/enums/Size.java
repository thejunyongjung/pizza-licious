package com.pluralsight.enums;

import java.math.BigDecimal;

public enum Size {
    PERSONAL_8 ("Personal 8\"", new BigDecimal("8.50")),
    MEDIUM_12 ("Medium 12\"", new BigDecimal("12.00")),
    LARGE_16 ("LARGE 16\"", new BigDecimal("16.50"));

    private final String displayName;
    private final BigDecimal basePrice;

    Size(String displayName, BigDecimal basePrice) {
        this.displayName = displayName;
        this.basePrice = basePrice;
    }

    public String getDisplayName() { return displayName; }

    public BigDecimal getBasePrice() { return basePrice; }

    @Override
    public String toString() { return displayName; }
}