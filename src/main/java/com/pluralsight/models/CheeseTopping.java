package com.pluralsight.models;

import java.math.BigDecimal;

public class CheeseTopping extends Topping {
    private Cheese cheese;

    public CheeseTopping(Cheese cheese, boolean extra) {
        super(extra);
        if (cheese == null) throw new IllegalArgumentException("Cheese cannot be null");
        this.cheese = cheese;
    }

    // Getter
    public Cheese getCheese() { return cheese; }

    // Setter
    public void setCheese(Cheese cheese) {
        if (cheese == null) throw new IllegalArgumentException("Cheese cannot be null");
        this.cheese = cheese;
    }

    @Override
    public BigDecimal getPrice(Size size) {
        // Determine base price
        // Replace switch statement with switch expression
        BigDecimal base = switch (size) {
            case PERSONAL_8 -> new BigDecimal("0.75");
            case MEDIUM_12  -> new BigDecimal("1.50");
            case LARGE_16   -> new BigDecimal("2.25");
        };
        // if no extra meat topping —> return base price
        if (!isExtra()) return base;

        // if extra cheese topping —> return base + extra
        BigDecimal extra = switch (size) {
            case PERSONAL_8 -> new BigDecimal("0.30");
            case MEDIUM_12  -> new BigDecimal("0.60");
            case LARGE_16   -> new BigDecimal("0.90");
        };
        return base.add(extra);
    }

    @Override
    public String getName() { return cheese.getDisplayName(); }
}