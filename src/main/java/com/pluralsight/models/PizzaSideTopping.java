package com.pluralsight.models;

import java.math.BigDecimal;

public class PizzaSideTopping extends Topping {
    private PizzaSide side;

    public PizzaSideTopping(PizzaSide side, boolean isExtra) {
        super(isExtra);
        this.side = side;
    }

    // Getter
    public PizzaSide getSide() { return side; }

    // Setter
    public void setSide(PizzaSide side) { this.side = side; }

    @Override
    public BigDecimal getPrice(Size size) { return BigDecimal.ZERO; } // ZERO —> a static constant
    // Sides are included —> No additional cost

    @Override
    public String getName() { return side.getDisplayName(); }
}