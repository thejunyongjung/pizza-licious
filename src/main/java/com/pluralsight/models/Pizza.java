package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A pizza on a customer's order.
 * Implements OrderItem so it can sit in the same list as Drink and GarlicKnots.
 */
public class Pizza implements OrderItem {
    private Size size;
    private CrustType crust;
    private boolean stuffedCrust;
    private List<Topping> toppings;

    public Pizza(Size size, CrustType crust, boolean stuffedCrust) {
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
    public void setSize(Size size) { this.size = size; }
    public void setCrust(CrustType crust) { this.crust = crust; }
    public void setStuffedCrust(boolean stuffedCrust) { this.stuffedCrust = stuffedCrust; }
    public void setToppings(List<Topping> toppings) { this.toppings = toppings; }

    // Topping management
    public void addTopping(Topping topping) { this.toppings.add(topping); }
    public void removeTopping(Topping topping) { this.toppings.remove(topping); }

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

    /** Receipt lines: header on top, toppings below. */
    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>();

        String header = "PIZZA: " + size.getDisplayName() + " "
                + crust.getDisplayName() + " Crust";
        if (stuffedCrust) header += " (Stuffed)";
        lines.add(header);

        if (!toppings.isEmpty()) {
            lines.add("  Toppings:");
            for (Topping t : toppings) {
                String prefix = t.isExtra() ? "Extra " : "";
                lines.add("    - " + prefix + t.getName());
            }
        }

        return lines.toArray(new String[0]);
    }

    /** Prints the receipt format when Java needs a string. */
    @Override
    public String toString() { return String.join("\n", getDescription()); }
}