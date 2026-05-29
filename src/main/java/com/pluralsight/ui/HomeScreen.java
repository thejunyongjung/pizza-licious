package com.pluralsight.ui;

import com.pluralsight.models.Order;
import com.pluralsight.util.Colors;

import java.util.Scanner;

/** The starting screen. Loops the main menu until the user exits. */
public class HomeScreen {
    private final Scanner scanner;

    public HomeScreen(Scanner scanner) { this.scanner = scanner; }

    /** Shows the menu in a loop until the user type '0'. */
    public void display() {
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    startNewOrder();
                    break;
                case "0":
                    System.out.println(Colors.brightCyan("\nThanks for visiting PIZZA-licious. Goodbye!"));
                    running = false;
                    break;
                default:
                    System.out.println(Colors.red("\nInvalid choice. Please enter 1 or 0."));
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println(Colors.bold("===== PIZZA-licious ====="));
        System.out.println("1) New Order");
        System.out.println("0) Exit");
        System.out.print("Enter your choice: ");
    }

    private void startNewOrder() {
        Order order = new Order();
        OrderScreen orderScreen = new OrderScreen(scanner);
        orderScreen.display(order);
    }
}