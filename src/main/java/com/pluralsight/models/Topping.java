package com.pluralsight.models;

import java.math.BigDecimal;

public abstract class Topping {
    // Naming rule: boolean field is without 'is'
    private boolean extra;

    protected Topping(boolean extra) { this.extra = extra; }

    // Naming rule: boolean methods is with 'is'
    // Getter
    public boolean isExtra() { return extra; }

    // Setter
    public void setExtra(boolean extra) { this.extra = extra; }

    // SUBCLASS SPECIFIC BEHAVIORS —> Must be overwritten
    public abstract BigDecimal getPrice(Size size);
    public abstract String getName();
}
