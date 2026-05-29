package com.pluralsight.ui;

import com.pluralsight.models.Drink;
import com.pluralsight.models.DrinkFlavor;
import com.pluralsight.models.DrinkSize;
import com.pluralsight.util.Colors;

import java.util.Scanner;

/** Picking a drink, size and flavor. */
public class AddDrinkScreen {
    private final Scanner scanner;

    public AddDrinkScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns the built Drink, or null if cancelled. */
    public Drink getDrink() {
        System.out.println(Colors.boldCyan("\n" + "=".repeat(24) + " Add Drink " + "=".repeat(25)));

        // Size
        DrinkSize size = promptDrinkSize();
        if (size == null) return null;

        // Flavor
        DrinkFlavor flavor = promptDrinkFlavor();
        if (flavor == null) return null;

        Drink drink = new Drink(size, flavor);
        System.out.println(Colors.green("\nDrink added:"));
        System.out.println(Colors.dim("-".repeat(60)));
        for (String line : drink.getDescription()) {
            System.out.println(line);
        }
        return drink;
    }

    // ===== Size & Flavor =====
    private DrinkSize promptDrinkSize() {
        DrinkSize[] sizes = DrinkSize.values();
        // Build options with prices so the menu stays in sync with DrinkSize
        String[] options = new String[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            options[i] = sizes[i].getDisplayName()
                    + " ($" + String.format("%.2f", sizes[i].getPrice()) + ")";
        }
        int index = promptChoice("Select drink size:", options, "Cancel");
        if (index == -1) return null;
        return sizes[index];
    }

    private DrinkFlavor promptDrinkFlavor() {
        DrinkFlavor[] flavors = DrinkFlavor.values();
        String[] options = getEnumLabels(flavors);
        int index = promptChoice("Select flavor:", options, "Cancel");
        if (index == -1) return null;
        return flavors[index];
    }

    // ===== Helpers =====

    /** Numbered menu. Returns the chosen index (or -1 to exit). */
    private int promptChoice(String header, String[] options, String exitLabel) {
        // Loop until valid input
        while (true) {
            System.out.println();
            System.out.println(Colors.bold(header));
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ") " + options[i]);
            }
            System.out.println("0) " + exitLabel);
            System.out.print("Choice: ");

            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number == 0) return -1;
                // Menu shows 1-based, array uses 0-based
                if (number >= 1 && number <= options.length) return number - 1;
                System.out.println("Choice must be between 0 and " + options.length + ".");
            } catch (NumberFormatException e) {
                // User typed a non-number
                System.out.println("Please enter a number.");
            }
        }
    }

    /** Returns the labels of an enum array. */
    private String[] getEnumLabels(Object[] enumValues) {
        // Every enum overrides toString to return its display name
        String[] labels = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            labels[i] = enumValues[i].toString();
        }
        return labels;
    }
}