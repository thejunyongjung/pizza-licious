package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;
import com.pluralsight.util.Colors;
import com.pluralsight.service.ReceiptWriter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/** Confirms the order and writes the receipt. */
public class CheckoutScreen {
    private static final int LINE_LENGTH = 60;

    private final Scanner scanner;

    public CheckoutScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns true if the customer confirmed and the receipt was saved. */
    public boolean display(Order order) {
        Colors.pause(700);
        System.out.println(Colors.boldMagenta("\n" + "=".repeat(25) + " Checkout " + "=".repeat(25)));
        System.out.println(Colors.dim(center("Order ID: " + order.getOrderId(), LINE_LENGTH)));
        System.out.println();

        printSummary(order);
        // Magenta line under the TOTAL
        System.out.println(Colors.magenta("-".repeat(LINE_LENGTH)));

        if (!promptYesNo("\nConfirm order? (y/n): ")) {
            System.out.println(Colors.yellow("\nCheckout cancelled."));
            return false;
        }

        ReceiptWriter writer = new ReceiptWriter();
        writer.save(order);

        System.out.println(Colors.green("\nThank you! Receipt saved."));
        return true;
    }

    // ===== UI =====
    private void printSummary(Order order) {
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            printItem(item);
            System.out.println();
        }
        // Default white line between items and totals
        System.out.println("-".repeat(LINE_LENGTH));
        System.out.println(formatLine("Subtotal",     order.getSubtotal()));
        System.out.println(formatLine("Tax (10.25%)", order.getTax()));
        System.out.println(Colors.boldBrightWhite(formatLine("TOTAL", order.getGrandTotal())));
    }

    /** Prints one item with price on the first line. */
    private void printItem(OrderItem item) {
        String priceString = String.format("$%.2f", item.getPrice());
        String[] lines = item.getDescription();

        // First line + price right-aligned
        String firstLine = lines[0];
        int spaces = LINE_LENGTH - firstLine.length() - priceString.length();
        if (spaces < 1) spaces = 1;
        System.out.println(colorizeItemLabel(firstLine) + " ".repeat(spaces) + priceString);

        // Remaining lines as-is (Pizza already pre-aligned them)
        for (int i = 1; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
    }

    /** Adds bold color to the section label (PIZZA/DRINK/GARLIC KNOTS/MARGHERITA/VEGGIE). */
    private String colorizeItemLabel(String line) {
        if (line.startsWith("PIZZA:") || line.startsWith("MARGHERITA:") || line.startsWith("VEGGIE:")) {
            int colonIdx = line.indexOf(":");
            return Colors.boldRed(line.substring(0, colonIdx)) + line.substring(colonIdx);
        }
        if (line.startsWith("DRINK:")) {
            return Colors.boldCyan("DRINK") + line.substring(5);
        }
        if (line.startsWith("GARLIC KNOTS:")) {
            return Colors.boldYellow("GARLIC KNOTS") + line.substring(12);
        }
        return line;
    }

    /** Right-aligns the amount within LINE_LENGTH. */
    private String formatLine(String label, BigDecimal amount) {
        String priceStr = "$" + String.format("%.2f", amount);
        int spaces = LINE_LENGTH - label.length() - priceStr.length();
        if (spaces < 1) spaces = 1;
        return label + " ".repeat(spaces) + priceStr;
    }

    /** Adds left padding so the text sits centered within `width` chars. */
    private String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        if (pad < 0) return text;
        return " ".repeat(pad) + text;
    }

    // ===== Helpers =====
    /** Yes/no prompt. Accepts y, yes, n, no (case-insensitive). */
    private boolean promptYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println(Colors.red("Please enter y or n."));
        }
    }
}