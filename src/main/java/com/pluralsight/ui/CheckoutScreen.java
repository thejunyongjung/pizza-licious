package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;
import com.pluralsight.util.ReceiptWriter;

import java.util.List;
import java.util.Scanner;

/** Confirms the order and writes the receipt. */
public class CheckoutScreen {
    private final Scanner scanner;

    public CheckoutScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns true if the customer confirmed and the receipt was saved. */
    public boolean display(Order order) {
        System.out.println("\n===== Checkout =====");
        printSummary(order);

        if (!promptYesNo("\nConfirm order? (y/n): ")) {
            System.out.println("\nCheckout cancelled.");
            return false;
        }

        ReceiptWriter writer = new ReceiptWriter();
        writer.save(order);

        System.out.println("\nThank you! Receipt saved.");
        return true;
    }

    // ===== UI =====
    private void printSummary(Order order) {
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            System.out.println("- " + item.getDescription()
                    + "  " + String.format("$%.2f", item.getPrice()));
        }
        System.out.println();
        System.out.println("Total: " + String.format("$%.2f", order.getTotal()));
    }

    // ===== Helpers =====
    /** Yes/no prompt. Accepts y, yes, n, no (case-insensitive). */
    private boolean promptYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter y or n.");
        }
    }
}