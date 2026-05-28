package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;
import com.pluralsight.util.ReceiptWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Confirms the order and writes the receipt. */
public class CheckoutScreen {
    private static final int LINE_LENGTH = 60;

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
            printItem(item);
            System.out.println();
        }
        System.out.println("Total: " + String.format("$%.2f", order.getTotal()));
    }

    /** Prints one item with price on the first line. */
    private void printItem(OrderItem item) {
        String priceString = String.format("$%.2f", item.getPrice());
        int maxLineLength = LINE_LENGTH - priceString.length() - 1;

        List<String> lines = new ArrayList<>();
        for (String line : item.getDescription()) {
            lines.addAll(Arrays.asList(splitIntoLines(line, maxLineLength)));
        }

        // First line + price right-aligned
        String firstLine = lines.get(0);
        int spaces = LINE_LENGTH - firstLine.length() - priceString.length();
        if (spaces < 1) spaces = 1;
        System.out.println(firstLine + " ".repeat(spaces) + priceString);

        // Remaining lines as-is
        for (int i = 1; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
    }

    /** Splits long text into shorter lines without breaking words. */
    private String[] splitIntoLines(String text, int maxLineLength) {
        // Short text? Return as-is so leading spaces stay.
        if (text.length() <= maxLineLength) {
            return new String[] { text };
        }

        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();
        for (String word : words) {
            int spaceBefore = !current.isEmpty() ? 1 : 0;
            if (current.length() + spaceBefore + word.length() > maxLineLength && !current.isEmpty()) {
                lines.add(current.toString());
                current = new StringBuilder();
            }
            if (!current.isEmpty()) current.append(" ");
            current.append(word);
        }
        if (!current.isEmpty()) lines.add(current.toString());
        return lines.toArray(new String[0]);
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