package com.pluralsight.models;

import com.pluralsight.enums.Meat;
import com.pluralsight.enums.Size;

import java.math.BigDecimal;

public class MeatTopping extends Topping {
    private Meat meat;

    public MeatTopping(Meat meat, boolean extra) {
        super(extra);
        if (meat == null) throw new IllegalArgumentException("Meat cannot be null");
        this.meat = meat;
    }

    // Getter
    public Meat getMeat() { return meat; }

    // Setter
    public void setMeat(Meat meat) {
        if (meat == null) throw new IllegalArgumentException("Meat cannot be null");
        this.meat = meat;
    }

    @Override
    public BigDecimal getPrice(Size size) {
        // Determine base price
        // Replace switch statement with switch expression
        BigDecimal base = switch (size) {
            case PERSONAL_8 -> new BigDecimal("1.00");
            case MEDIUM_12  -> new BigDecimal("2.00");
            case LARGE_16   -> new BigDecimal("3.00");
        };

        // if no extra meat topping —> return base price
        if (!isExtra()) return base;

        // if extra meat topping —> return base + extra
        BigDecimal extra = switch (size) {
            case PERSONAL_8 -> new BigDecimal("0.50");
            case MEDIUM_12  -> new BigDecimal("1.00");
            case LARGE_16   -> new BigDecimal("1.50");
        };
        return base.add(extra);
    }

    @Override
    public String getName() { return meat.getDisplayName(); }
}