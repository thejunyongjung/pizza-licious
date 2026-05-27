package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;

import java.util.List;
import java.util.Scanner;

/** The screen for building an order. */
public class OrderScreen {
    private final Scanner scanner;

    public OrderScreen(Scanner scanner) { this.scanner = scanner; }

    /** Loops the order menu until the user checks out or cancels. */
    public void display(Order order) {
        boolean ordering = true;
        while (ordering) {
            printOrder(order);
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addPizza(order);
                    break;
                case "2":
                    addDrink(order);
                    break;
                case "3":
                    addKnots(order);
                    break;
                case "4":
                    if (checkout(order)) {
                        ordering = false;
                    }
                    break;
                case "0":
                    System.out.println("\nOrder cancelled.");
                    ordering = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter 1-4 or 0.");
            }
        }
    }

    // SCREEN UI
    private void printOrder(Order order) {
        System.out.println();
        System.out.println("===== Current Order =====");
        List<OrderItem> items = order.getItemsNewestFirst();
        if (items.isEmpty()) {
            System.out.println("(no items yet)");
        } else {
            for (OrderItem item : items) {
                System.out.println("- " + item.getDescription()
                        + "  " + String.format("$%.2f", item.getPrice()));
            }
        }
        System.out.println();
        System.out.println("Total: " + String.format("$%.2f", order.getTotal()));
    }

    // MENU UI
    private void printMenu() {
        System.out.println();
        System.out.println("1) Add Pizza");
        System.out.println("2) Add Drink");
        System.out.println("3) Add Garlic Knots");
        System.out.println("4) Checkout");
        System.out.println("0) Cancel Order");
        System.out.print("Enter your choice: ");
    }

    // ACTIONS
    private void addPizza(Order order) {
        // TODO P13: launch AddPizzaScreen, add the result to the order
        System.out.println("\n[...Add Pizza screen coming in P13]");
    }

    private void addDrink(Order order) {
        // TODO P14: launch AddDrinkScreen, add the result to the order
        System.out.println("\n[...Add Drink screen coming in P14]");
    }

    private void addKnots(Order order) {
        // TODO P15: launch AddGarlicKnotsScreen, add the result to the order
        System.out.println("\n[...Add Garlic Knots screen coming in P15]");
    }

    /** Returns true if checkout succeeded so the order loop should exit. */
    private boolean checkout(Order order) {
        // TODO P16: launch CheckoutScreen for confirm + ReceiptWriter
        if (!order.isValid()) {
            System.out.println("\nCannot check out an empty order. Add an item first.");
            return false;
        }
        System.out.println("\n[...Checkout screen coming in P16]");
        return true;
    }
}
