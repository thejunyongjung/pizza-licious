package com.pluralsight.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * A customer's order. Holds Pizzas, Drinks, and GarlicKnots in one list.
 * Heterogeneous collection and polymorphism via OrderItem.
 */
public class Order {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.1025");   // Fremont, CA

    private final String orderId;
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
        // Order ID: date + short UUID — sortable by date, still unique
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String shortUuid = UUID.randomUUID().toString().substring(0, 8);
        this.orderId = date + "-" + shortUuid;
    }

    // Getter
    public String getOrderId() { return orderId; }

    /** Returns a defensive copy so callers can't mutate the internal list. */
    public List<OrderItem> getItems() { return new ArrayList<>(items); }

    // Setter
    /** Stores a defensive copy so callers can't mutate Order's state via their original list. */
    public void setItems(List<OrderItem> items) {
        if (items == null) throw new IllegalArgumentException("Items cannot be null");
        this.items = new ArrayList<>(items);
    }

    // Item management
    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
    }
    public void removeItem(int index) { items.remove(index); }
    public void clear() { items.clear(); }

    // Order calculations and queries

    /** Sum of all items before tax. */
    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getPrice());
        }
        return total;
    }

    /** Fremont tax (10.25%), rounded to 2 decimals. */
    public BigDecimal getTax() {
        return getSubtotal().multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    /** Subtotal + tax — what the customer actually pays. */
    public BigDecimal getGrandTotal() {
        return getSubtotal().add(getTax());
    }

    /** Backward-compat alias for getSubtotal — existing tests and UI rely on this. */
    public BigDecimal getTotal() { return getSubtotal(); }

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