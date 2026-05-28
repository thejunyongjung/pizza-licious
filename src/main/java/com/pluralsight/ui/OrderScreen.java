package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;

import com.pluralsight.models.Pizza;
import com.pluralsight.models.Drink;
import com.pluralsight.models.GarlicKnots;

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
        AddPizzaScreen addPizzaScreen = new AddPizzaScreen(scanner);
        Pizza pizza = addPizzaScreen.getPizza();
        if (pizza != null) {
            order.addItem(pizza);
        }
    }

    private void addDrink(Order order) {
        AddDrinkScreen addDrinkScreen = new AddDrinkScreen(scanner);
        Drink drink = addDrinkScreen.getDrink();
        if (drink != null) {
            order.addItem(drink);
        }
    }

    private void addKnots(Order order) {
        AddGarlicKnotsScreen addKnotsScreen = new AddGarlicKnotsScreen(scanner);
        GarlicKnots knots = addKnotsScreen.getKnots();
        if (knots != null) {
            order.addItem(knots);
        }
    }

    /** Returns true if checkout succeeded so the order loop should exit. */
    private boolean checkout(Order order) {
        if (!order.isValid()) {
            System.out.println("\nCannot check out an empty order. Add an item first.");
            return false;
        }
        CheckoutScreen checkoutScreen = new CheckoutScreen(scanner);
        return checkoutScreen.display(order);
    }
}
