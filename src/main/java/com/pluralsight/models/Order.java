package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A customer's order. Holds Pizzas, Drinks, and GarlicKnots in one list.
 * Heterogeneous collection and polymorphism via OrderItem.
 */
public class Order {
    private List<OrderItem> items;

    public Order() { items = new ArrayList<>(); }

    // Getter
    /** Returns a defensive copy so callers can't mutate the internal list. */
    public List<OrderItem> getItems() { return new ArrayList<>(items); }

    // Setter
    /** Stores a defensive copy so callers can't mutate Order's state via their original list. */
    public void setItems(List<OrderItem> items) { this.items = new ArrayList<>(items); }

    // Item management
    public void addItem(OrderItem item) { items.add(item); }
    public void removeItem(int index) { items.remove(index); }
    public void clear() { items.clear();}

    // Order calculations and queries
    /** Sums every item's price. Works for any OrderItem subtype. */
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getPrice());
        }
        return total;
    }

    /** Newest item first. Used by the Order screen so the most recent add shows on top. */
    public List<OrderItem> getItemsNewestFirst() {
        List<OrderItem> reversedList = new ArrayList<>(items);
        Collections.reverse(reversedList);
        return reversedList;
    }

    /**
     * True if the order has at least one item.
     * Business rule: must contain at least one pizza, drink, or garlic knots.
     */
    public boolean isValid() {
        return !items.isEmpty();
    }
}
