package com.pluralsight.ui;

import com.pluralsight.models.Topping;
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
    private static final int MAX_TOPPINGS = 7;

    private final Scanner scanner;

    public AddPizzaScreen(Scanner scanner) { this.scanner = scanner; }

    /** Returns the built Pizza, or null if cancelled at crust/size or invalid. */
    public Pizza getPizza() {
        System.out.println("\n===== Add Pizza =====");

        // STEP 0: Variant (Build your own, Margherita, Veggie)
        int variant = promptPizzaType();
        if (variant == -1) return null;

        // Signature pizzas — everything preset
        if (variant == 1) {
            Pizza pizza = new Margherita();
            announceAdded(pizza);
            return pizza;
        }
        if (variant == 2) {
            Pizza pizza = new Veggie();
            announceAdded(pizza);
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

        // Require at least one topping
        if (pizza.getToppings().isEmpty()) {
            System.out.println("\nA pizza must have at least one topping. Pizza not added.");
            return null;
        }

        // Require at least one sauce
        boolean hasSauce = false;
        for (Topping t : pizza.getToppings()) {
            if (t instanceof Sauce) {
                hasSauce = true;
                break;
            }
        }
        if (!hasSauce) {
            System.out.println("\nA pizza must have at least one sauce. Pizza not added.");
            return null;
        }

        pizza.setStuffedCrust(promptYesNo("\nStuffed crust? (y/n): "));

        announceAdded(pizza);
        return pizza;
    }

    /** Prints "Pizza added:" with the receipt-style block. */
    private void announceAdded(Pizza pizza) {
        System.out.println("\nPizza added:");
        for (String line : pizza.getDescription()) {
            System.out.println(line);
        }
    }

    // ===== Pizza Type =====
    /** Asks which pizza type: 0 = Build your own, 1 = Margherita, 2 = Veggie. */
    private int promptPizzaType() {
        String[] options = {
                "Build your own",
                "Margherita (12\" Regular — Mozzarella, Tomatoes, Basil, Marinara, Olive Oil)",
                "Veggie (8\" Regular — Mozzarella, Bell Peppers, Spinach, Olives, Onions, Marinara)"
        };
        return promptChoice("Pizza type:", options, "Cancel");
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
        if (pizza.getToppings().size() >= MAX_TOPPINGS) return;
        System.out.println("\n----- Meats (priced per size) -----");
        Meat[] meats = Meat.values();
        String[] options = getEnumLabels(meats);
        while (pizza.getToppings().size() < MAX_TOPPINGS) {
            int index = promptChoice("Select meat:", options, "Done with meats");
            if (index == -1) break;
            Meat selected = meats[index];
            if (hasMeat(pizza, selected)) {
                System.out.println(selected.getDisplayName() + " is already on this pizza.");
                continue;
            }
            boolean extra = promptYesNo("Extra? (y/n): ");
            pizza.addTopping(new MeatTopping(selected, extra));
        }
    }

    private void addCheeses(Pizza pizza) {
        if (pizza.getToppings().size() >= MAX_TOPPINGS) return;
        System.out.println("\n----- Cheeses (priced per size) -----");
        Cheese[] cheeses = Cheese.values();
        String[] options = getEnumLabels(cheeses);
        while (pizza.getToppings().size() < MAX_TOPPINGS) {
            int index = promptChoice("Select cheese:", options, "Done with cheeses");
            if (index == -1) break;
            Cheese selected = cheeses[index];
            if (hasCheese(pizza, selected)) {
                System.out.println(selected.getDisplayName() + " is already on this pizza.");
                continue;
            }
            boolean extra = promptYesNo("Extra? (y/n): ");
            pizza.addTopping(new CheeseTopping(selected, extra));
        }
    }

    private void addRegularToppings(Pizza pizza) {
        if (pizza.getToppings().size() >= MAX_TOPPINGS) return;
        System.out.println("\n----- Regular Toppings (free) -----");
        RegularToppingType[] types = RegularToppingType.values();
        String[] options = getEnumLabels(types);
        while (pizza.getToppings().size() < MAX_TOPPINGS) {
            int index = promptChoice("Select topping:", options, "Done with toppings");
            if (index == -1) break;
            RegularToppingType selected = types[index];
            if (hasRegularTopping(pizza, selected)) {
                System.out.println(selected.getDisplayName() + " is already on this pizza.");
                continue;
            }
            pizza.addTopping(new RegularTopping(selected, false));
        }
    }

    private void addSauces(Pizza pizza) {
        if (pizza.getToppings().size() >= MAX_TOPPINGS) return;
        System.out.println("\n----- Sauces (free) -----");
        SauceType[] sauces = SauceType.values();
        String[] options = getEnumLabels(sauces);
        while (pizza.getToppings().size() < MAX_TOPPINGS) {
            int index = promptChoice("Select sauce:", options, "Done with sauces");
            if (index == -1) break;
            SauceType selected = sauces[index];
            if (hasSauce(pizza, selected)) {
                System.out.println(selected.getDisplayName() + " is already on this pizza.");
                continue;
            }
            pizza.addTopping(new Sauce(selected, false));
        }
    }

    private void addSides(Pizza pizza) {
        if (pizza.getToppings().size() >= MAX_TOPPINGS) return;
        System.out.println("\n----- Sides (free) -----");
        PizzaSide[] sides = PizzaSide.values();
        String[] options = getEnumLabels(sides);
        while (pizza.getToppings().size() < MAX_TOPPINGS) {
            int index = promptChoice("Select side:", options, "Done with sides");
            if (index == -1) break;
            PizzaSide selected = sides[index];
            if (hasSide(pizza, selected)) {
                System.out.println(selected.getDisplayName() + " is already on this pizza.");
                continue;
            }
            pizza.addTopping(new PizzaSideTopping(selected, false));
        }
    }

    // ===== Duplicate-check helpers =====
    private boolean hasMeat(Pizza pizza, Meat meat) {
        for (Topping t : pizza.getToppings()) {
            if (t instanceof MeatTopping && ((MeatTopping) t).getMeat() == meat) return true;
        }
        return false;
    }

    private boolean hasCheese(Pizza pizza, Cheese cheese) {
        for (Topping t : pizza.getToppings()) {
            if (t instanceof CheeseTopping && ((CheeseTopping) t).getCheese() == cheese) return true;
        }
        return false;
    }

    private boolean hasRegularTopping(Pizza pizza, RegularToppingType type) {
        for (Topping t : pizza.getToppings()) {
            if (t instanceof RegularTopping && ((RegularTopping) t).getType() == type) return true;
        }
        return false;
    }

    private boolean hasSauce(Pizza pizza, SauceType sauce) {
        for (Topping t : pizza.getToppings()) {
            if (t instanceof Sauce && ((Sauce) t).getSauce() == sauce) return true;
        }
        return false;
    }

    private boolean hasSide(Pizza pizza, PizzaSide side) {
        for (Topping t : pizza.getToppings()) {
            if (t instanceof PizzaSideTopping && ((PizzaSideTopping) t).getSide() == side) return true;
        }
        return false;
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