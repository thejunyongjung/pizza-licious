package com.pluralsight.ui;

import com.pluralsight.models.Pizza;
import com.pluralsight.models.Size;
import com.pluralsight.models.CrustType;
import com.pluralsight.models.Meat;
import com.pluralsight.models.MeatTopping;
import com.pluralsight.models.PizzaSide;
import com.pluralsight.models.PizzaSideTopping;
import com.pluralsight.models.Sauce;
import com.pluralsight.models.SauceType;
import com.pluralsight.models.RegularTopping;
import com.pluralsight.models.RegularToppingType;
import com.pluralsight.models.Cheese;
import com.pluralsight.models.CheeseTopping;

import com.pluralsight.models.Margherita;
import com.pluralsight.models.Veggie;

import java.util.Scanner;

/** Making a pizza, step by step. */
public class AddPizzaScreen {
    private final Scanner scanner;

    public AddPizzaScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns the built Pizza, or null if cancelled at crust/size. */
    public Pizza getPizza() {
        System.out.println("\n===== Add Pizza =====");

        // STEP 0: Variant (Build your own, Margherita, Veggie)
        int variant = promptPizzaType();
        if (variant == -1) return null;

        // Signature pizzas — everything preset, just confirm and return
        if (variant == 1) {
            Pizza pizza = new Margherita();
            System.out.println("\nPizza added:");
            for (String line : pizza.getDescription()) {
                System.out.println(line);
            }
            return pizza;
        }
        if (variant == 2) {
            Pizza pizza = new Veggie();
            System.out.println("\nPizza added:");
            for (String line : pizza.getDescription()) {
                System.out.println(line);
            }
            return pizza;
        }

        // Build your own — full 8-step flow
        CrustType crust = promptCrust();
        if (crust == null) return null;

        Size size = promptSize();
        if (size == null) return null;

        Pizza pizza = new Pizza(size, crust, false);

        addMeats(pizza);
        addCheeses(pizza);
        addRegularToppings(pizza);
        addSauces(pizza);
        addSides(pizza);

        pizza.setStuffedCrust(promptYesNo("\nStuffed crust? (y/n): "));

        System.out.println("\nPizza added:");
        for (String line : pizza.getDescription()) {
            System.out.println(line);
        }
        return pizza;
    }
    // ===== Crust & Size =====
    private CrustType promptCrust() {
        CrustType[] crusts = CrustType.values();
        String[] options = getEnumLabels(crusts);
        int index = promptChoice("Select crust:", options, "Cancel");
        if (index == -1) return null;
        return crusts[index];
    }

    private Size promptSize() {
        Size[] sizes = Size.values();
        // Build options with prices so the menu stays in sync with Size
        String[] options = new String[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            options[i] = sizes[i].getDisplayName()
                    + " ($" + String.format("%.2f", sizes[i].getBasePrice()) + ")";
        }
        int index = promptChoice("Select size:", options, "Cancel");
        if (index == -1) return null;
        return sizes[index];
    }

    // ===== Topping loops =====
    private void addMeats(Pizza pizza) {
        System.out.println("\n----- Meats (priced per size) -----");
        Meat[] meats = Meat.values();
        String[] options = getEnumLabels(meats);
        while (true) {
            int index = promptChoice("Select meat:", options, "Done with meats");
            if (index == -1) break;
            boolean extra = promptYesNo("Extra? (y/n): ");
            pizza.addTopping(new MeatTopping(meats[index], extra));
        }
    }

    private void addCheeses(Pizza pizza) {
        System.out.println("\n----- Cheeses (priced per size) -----");
        Cheese[] cheeses = Cheese.values();
        String[] options = getEnumLabels(cheeses);
        while (true) {
            int index = promptChoice("Select cheese:", options, "Done with cheeses");
            if (index == -1) break;
            boolean extra = promptYesNo("Extra? (y/n): ");
            pizza.addTopping(new CheeseTopping(cheeses[index], extra));
        }
    }

    private void addRegularToppings(Pizza pizza) {
        System.out.println("\n----- Regular Toppings (free) -----");
        RegularToppingType[] types = RegularToppingType.values();
        String[] options = getEnumLabels(types);
        while (true) {
            int index = promptChoice("Select topping:", options, "Done with toppings");
            if (index == -1) break;
            pizza.addTopping(new RegularTopping(types[index], false));
        }
    }

    private void addSauces(Pizza pizza) {
        System.out.println("\n----- Sauces (free) -----");
        SauceType[] sauces = SauceType.values();
        String[] options = getEnumLabels(sauces);
        while (true) {
            int index = promptChoice("Select sauce:", options, "Done with sauces");
            if (index == -1) break;
            pizza.addTopping(new Sauce(sauces[index], false));
        }
    }

    private void addSides(Pizza pizza) {
        System.out.println("\n----- Sides (free) -----");
        PizzaSide[] sides = PizzaSide.values();
        String[] options = getEnumLabels(sides);
        while (true) {
            int index = promptChoice("Select side:", options, "Done with sides");
            if (index == -1) break;
            pizza.addTopping(new PizzaSideTopping(sides[index], false));
        }
    }

    // ===== Helpers =====

    /** Numbered menu. Returns the chosen index (or -1 to exit). */
    private int promptChoice(String header, String[] options, String exitLabel) {
        // Loop until valid input
        while (true) {
            System.out.println();
            System.out.println(header);
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

    // ===== Variant =====
    /** Asks which pizza type: 0 = Build your own, 1 = Margherita, 2 = Veggie. */
    private int promptPizzaType() {
        String[] options = {
                "Build your own",
                "Margherita (12\" Regular — Mozzarella, Tomatoes, Basil, Marinara, Olive Oil)",
                "Veggie (8\" Regular — Mozzarella, Bell Peppers, Spinach, Olives, Onions, Marinara)"
        };
        return promptChoice("Pizza variant:", options, "Cancel");
    }

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