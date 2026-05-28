package com.pluralsight.models;

/**
 * Classic Margherita — fixed at 12" Regular crust per the menu.
 * Size, crust, and toppings are all preset
 * No customization.
 */
public class Margherita extends Pizza {

    public Margherita() {
        super(Size.MEDIUM_12, CrustType.REGULAR, false);
        addTopping(new CheeseTopping(Cheese.MOZZARELLA, false));
        addTopping(new RegularTopping(RegularToppingType.TOMATOES, false));
        addTopping(new RegularTopping(RegularToppingType.BASIL, false));
        addTopping(new Sauce(SauceType.MARINARA, false));
        addTopping(new Sauce(SauceType.OLIVE_OIL, false));
    }

    /** Receipt header for Margherita. */
    @Override
    protected String getPizzaType() {
        return "MARGHERITA";
    }
}