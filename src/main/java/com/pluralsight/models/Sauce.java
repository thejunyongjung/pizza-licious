package com.pluralsight.models;

import java.math.BigDecimal;

public class Sauce extends Topping {
    private SauceType sauce;

    public Sauce(SauceType sauce, boolean isExtra) {
        super(isExtra);
        this.sauce = sauce;
    }

    // Getter
    public SauceType getSauce() { return sauce; }

    // Setter
    public void setSauce(SauceType sauce) { this.sauce = sauce; }

    @Override
    public BigDecimal getPrice(Size size) { return BigDecimal.ZERO; } // ZERO —> a static constant
    // Sauces are included —> No additional cost

    @Override
    public String getName() { return sauce.getDisplayName(); }
}
