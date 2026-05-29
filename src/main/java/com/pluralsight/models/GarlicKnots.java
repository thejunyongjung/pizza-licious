package com.pluralsight.models;

import java.math.BigDecimal;

/**
 * Garlic knots side order.
 * Implements OrderItem so it can sit in the same list as Pizza and Drink.
 */
public class GarlicKnots implements OrderItem {
    // Making each knot's price as static constant
    private static final BigDecimal KNOT_PRICE = new BigDecimal("1.50");

    private int quantity;

    public GarlicKnots(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1");
        this.quantity = quantity;
    }

    // Getter
    public int getQuantity() { return quantity; }

    // Setter
    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1");
        this.quantity = quantity;
    }

    /** Quantity times the knot price ($1.50 each). */
    @Override
    public BigDecimal getPrice() { return KNOT_PRICE.multiply(BigDecimal.valueOf(quantity)); }

    /** Receipt line for the knots. */
    @Override
    public String[] getDescription() {
        String unit = (quantity == 1) ? "piece" : "pieces";
        return new String[] { "GARLIC KNOTS: " + quantity + " " + unit };
    }

    /** Prints the receipt format when Java needs a string. */
    @Override
    public String toString() { return String.join("\n", getDescription()); }
}