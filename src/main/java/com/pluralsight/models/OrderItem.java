package com.pluralsight.models;

import java.math.BigDecimal;

/** A product that can be placed in an Order.
 *  Used by Order to total prices and print receipts. */

public interface OrderItem {

    /**
     * @return the price of this item in dollars
     */
    BigDecimal getPrice();

    /**
     * @return a description used on the receipt
     */
    String getDescription();
}
