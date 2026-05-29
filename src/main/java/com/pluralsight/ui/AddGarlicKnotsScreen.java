package com.pluralsight.ui;

import com.pluralsight.models.GarlicKnots;
import com.pluralsight.util.Colors;

import java.util.Scanner;

/** Asking how many garlic knots. */
public class AddGarlicKnotsScreen {
    private final Scanner scanner;

    public AddGarlicKnotsScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns the built GarlicKnots, or null if cancelled. */
    public GarlicKnots getKnots() {
        Colors.pause(700);
        System.out.println(Colors.boldYellow("\n" + "=".repeat(21) + " Add Garlic Knots " + "=".repeat(21)));
        System.out.println("$1.50 each");

        int quantity = promptQuantity();
        if (quantity == -1) return null;

        GarlicKnots knots = new GarlicKnots(quantity);
        System.out.println(Colors.green("\nGarlic Knots added:"));
        System.out.println(Colors.dim("-".repeat(60)));
        for (String line : knots.getDescription()) {
            System.out.println(line);
        }
        return knots;
    }

    /** Quantity prompt. Returns the chosen quantity (1+), or -1 to cancel. */
    private int promptQuantity() {
        // Loop until valid input
        while (true) {
            System.out.println();
            System.out.println(Colors.bold("How many garlic knots? (enter 0 to cancel)"));
            System.out.print("Quantity: ");

            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number == 0) return -1;
                if (number >= 1) return number;
                System.out.println("Quantity must be 1 or more.");
            } catch (NumberFormatException e) {
                // User typed a non-number
                System.out.println("Please enter a number.");
            }
        }
    }
}