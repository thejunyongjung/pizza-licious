package com.pluralsight.models;

import com.pluralsight.enums.SauceType;
import com.pluralsight.enums.Size;

import java.math.BigDecimal;

public class Sauce extends Topping {
    private SauceType sauce;

    public Sauce(SauceType sauce, boolean isExtra) {
        super(isExtra);
        if (sauce == null) throw new IllegalArgumentException("Sauce cannot be null");
        this.sauce = sauce;
    }

    // Getter
    public SauceType getSauce() { return sauce; }

    // Setter
    public void setSauce(SauceType sauce) {
        if (sauce == null) throw new IllegalArgumentException("Sauce cannot be null");
        this.sauce = sauce;
    }

    @Override
    public BigDecimal getPrice(Size size) { return BigDecimal.ZERO; } // ZERO —> a static constant
    // Sauces are included —> No additional cost

    @Override
    public String getName() { return sauce.getDisplayName(); }
}