package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;
import com.pluralsight.models.Pizza;
import com.pluralsight.models.Drink;
import com.pluralsight.models.GarlicKnots;
import com.pluralsight.util.Colors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/** The screen for building an order. */
public class OrderScreen {
    private static final int LINE_LENGTH = 60;

    private final Scanner scanner;

    public OrderScreen(Scanner scanner) { this.scanner = scanner; }

    public void display(Order order) {
        boolean ordering = true;
        while (ordering) {
            printOrder(order);
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": addPizza(order); break;
                case "2": addDrink(order); break;
                case "3": addKnots(order); break;
                case "4":
                    if (checkout(order)) ordering = false;
                    break;
                case "0":
                    System.out.println(Colors.yellow("\nOrder cancelled."));
                    ordering = false;
                    break;
                default:
                    System.out.println(Colors.red("\nInvalid choice. Please enter 1-4 or 0."));
            }
        }
    }

    private void printOrder(Order order) {
        System.out.println();
        System.out.println(Colors.boldCyan("=".repeat(22) + " Current Order " + "=".repeat(23)));

        List<OrderItem> items = order.getItemsNewestFirst();
        if (items.isEmpty()) {
            System.out.println("(no items yet)");
        } else {
            for (OrderItem item : items) {
                printItem(item);
                System.out.println();
            }
        }

        // Default white line between items and total
        System.out.println("-".repeat(LINE_LENGTH));
        // Right-aligned total
        System.out.println(formatTotalLine("Total:", order.getTotal()));
        // Cyan line under total
        System.out.println(Colors.cyan("-".repeat(LINE_LENGTH)));
    }

    private String formatTotalLine(String label, BigDecimal total) {
        String priceStr = String.format("$%.2f", total);
        int spaces = LINE_LENGTH - label.length() - priceStr.length();
        if (spaces < 1) spaces = 1;
        return label + " ".repeat(spaces) + priceStr;
    }

    private void printItem(OrderItem item) {
        String priceString = String.format("$%.2f", item.getPrice());
        String[] lines = item.getDescription();

        String firstLine = lines[0];
        int spaces = LINE_LENGTH - firstLine.length() - priceString.length();
        if (spaces < 1) spaces = 1;
        System.out.println(colorizeItemLabel(firstLine) + " ".repeat(spaces) + priceString);

        for (int i = 1; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
    }

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

    private void printMenu() {
        System.out.println();
        System.out.println("1) Add Pizza");
        System.out.println("2) Add Drink");
        System.out.println("3) Add Garlic Knots");
        System.out.println("4) Checkout");
        System.out.println("0) Cancel Order");
        System.out.print("Enter your choice: ");
    }

    private void addPizza(Order order) {
        AddPizzaScreen addPizzaScreen = new AddPizzaScreen(scanner);
        Pizza pizza = addPizzaScreen.getPizza();
        if (pizza != null) order.addItem(pizza);
    }

    private void addDrink(Order order) {
        AddDrinkScreen addDrinkScreen = new AddDrinkScreen(scanner);
        Drink drink = addDrinkScreen.getDrink();
        if (drink != null) order.addItem(drink);
    }

    private void addKnots(Order order) {
        AddGarlicKnotsScreen addKnotsScreen = new AddGarlicKnotsScreen(scanner);
        GarlicKnots knots = addKnotsScreen.getKnots();
        if (knots != null) order.addItem(knots);
    }

    private boolean checkout(Order order) {
        if (!order.isValid()) {
            System.out.println(Colors.red("\nCannot check out an empty order. Add an item first."));
            return false;
        }
        CheckoutScreen checkoutScreen = new CheckoutScreen(scanner);
        return checkoutScreen.display(order);
    }
}