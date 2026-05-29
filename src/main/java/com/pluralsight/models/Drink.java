package com.pluralsight.models;

import java.math.BigDecimal;

/**
 * A drink on a customer's order.
 * Implements OrderItem so it can sit in the same list as Pizza and GarlicKnots.
 */
public class Drink implements OrderItem {
    private DrinkSize size;
    private DrinkFlavor flavor;

    public Drink(DrinkSize size, DrinkFlavor flavor) {
        // Reject invalid input
        if (size == null) throw new IllegalArgumentException("Size cannot be null");
        if (flavor == null) throw new IllegalArgumentException("Flavor cannot be null");

        this.size = size;
        this.flavor = flavor;
    }

    // Getters
    public DrinkSize getSize() { return size; }
    public DrinkFlavor getFlavor() { return flavor; }

    // Setters
    public void setSize(DrinkSize size) {
        if (size == null) throw new IllegalArgumentException("Size cannot be null");
        this.size = size;
    }
    public void setFlavor(DrinkFlavor flavor) {
        if (flavor == null) throw new IllegalArgumentException("Flavor cannot be null");
        this.flavor = flavor;
    }

    // OrderItem implementation
    /** Reads the price directly from the drink's size. */
    @Override
    public BigDecimal getPrice() { return size.getPrice(); }

    /** Receipt line for the drink. */
    @Override
    public String[] getDescription() {
        return new String[] { "DRINK: " + size.getDisplayName() + " " + flavor.getDisplayName() };
    }

    /** Prints the receipt format when Java needs a string. */
    @Override
    public String toString() { return String.join("\n", getDescription()); }
}