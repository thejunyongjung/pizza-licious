package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A pizza on a customer's order.
 * Implements OrderItem so it can sit in the same list as Drink and GarlicKnots.
 */
public class Pizza implements OrderItem {
    private static final int LINE_LENGTH = 60;   // matches ReceiptWriter's line width

    private Size size;
    private CrustType crust;
    private boolean stuffedCrust;
    private List<Topping> toppings;

    public Pizza(Size size, CrustType crust, boolean stuffedCrust) {
        // Reject invalid input
        if (size == null) throw new IllegalArgumentException("Size cannot be null");
        if (crust == null) throw new IllegalArgumentException("Crust cannot be null");

        this.size = size;
        this.crust = crust;
        this.stuffedCrust = stuffedCrust;
        this.toppings = new ArrayList<>();
    }

    // Getters
    public Size getSize() { return size; }
    public CrustType getCrust() { return crust; }
    public boolean isStuffedCrust() { return stuffedCrust; }
    public List<Topping> getToppings() { return toppings; }

    // Setters
    public void setSize(Size size) {
        if (size == null) throw new IllegalArgumentException("Size cannot be null");
        this.size = size;
    }
    public void setCrust(CrustType crust) {
        if (crust == null) throw new IllegalArgumentException("Crust cannot be null");
        this.crust = crust;
    }
    public void setStuffedCrust(boolean stuffedCrust) { this.stuffedCrust = stuffedCrust; }
    public void setToppings(List<Topping> toppings) {
        if (toppings == null) throw new IllegalArgumentException("Toppings cannot be null");
        this.toppings = toppings;
    }

    // Topping management
    public void addTopping(Topping topping) {
        if (topping == null) throw new IllegalArgumentException("Topping cannot be null");
        this.toppings.add(topping);
    }
    public void removeTopping(Topping topping) { this.toppings.remove(topping); }

    /** Type label for the receipt header. Subclasses override. */
    protected String getPizzaType() {
        return "PIZZA";
    }

    // OrderItem implementation
    /** Adds up the size base price and every topping. */
    @Override
    public BigDecimal getPrice() {
        BigDecimal total = size.getBasePrice();
        for (Topping t : toppings) {
            total = total.add(t.getPrice(size));
        }
        return total;
    }

    /** Receipt lines: total on top, breakdown below (amounts in parens). */
    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>();

        // Header (charged total added by ReceiptWriter)
        String header = getPizzaType() + ": " + size.getDisplayName() + " "
                + crust.getDisplayName() + " Crust";
        if (stuffedCrust) header += " (Stuffed)";
        lines.add(header);

        // Base price
        lines.add(rightAlign("  Base price",
                "($" + String.format("%.2f", size.getBasePrice()) + ")"));

        // Toppings
        if (!toppings.isEmpty()) {
            lines.add("  Toppings:");
            for (Topping t : toppings) {
                String prefix = t.isExtra() ? "Extra " : "";
                String name = "    - " + prefix + t.getName();

                BigDecimal price = t.getPrice(size);
                if (price.compareTo(BigDecimal.ZERO) > 0) {
                    lines.add(rightAlign(name,
                            "($" + String.format("%.2f", price) + ")"));
                } else {
                    lines.add(name);   // Free topping —> name only
                }
            }
        }

        return lines.toArray(new String[0]);
    }

    /** Pads label with spaces so the price sits at LINE_LENGTH (right-aligned). */
    private String rightAlign(String label, String price) {
        int spaces = LINE_LENGTH - label.length() - price.length();
        if (spaces < 1) spaces = 1;
        return label + " ".repeat(spaces) + price;
    }

    /** Prints the receipt format when Java needs a string. */
    @Override
    public String toString() { return String.join("\n", getDescription()); }
}