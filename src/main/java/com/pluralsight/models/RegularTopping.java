package com.pluralsight.models;

import java.math.BigDecimal;

public class RegularTopping extends Topping {
    private RegularToppingType type;

    public RegularTopping(RegularToppingType type, boolean extra) {
        super(extra);
        if (type == null) throw new IllegalArgumentException("Topping type cannot be null");
        this.type = type;
    }

    // Getter
    public RegularToppingType getType() { return type; }

    // Setter
    public void setType(RegularToppingType type) {
        if (type == null) throw new IllegalArgumentException("Topping type cannot be null");
        this.type = type;
    }

    @Override
    public BigDecimal getPrice(Size size) { return BigDecimal.ZERO; } // ZERO —> a static constant
    // Regular toppings are included —> No additional cost

    @Override
    public String getName() { return type.getDisplayName(); }
}