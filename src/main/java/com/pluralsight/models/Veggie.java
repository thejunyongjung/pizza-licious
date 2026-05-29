package com.pluralsight.models;

import com.pluralsight.enums.*;

/**
 * Veggie pizza — fixed at 8" Regular crust per the menu.
 * Size, crust, and toppings are all preset
 * No customization.
 */
public class Veggie extends Pizza {

    public Veggie() {
        super(Size.PERSONAL_8, CrustType.REGULAR, false);
        addTopping(new CheeseTopping(Cheese.MOZZARELLA, false));
        addTopping(new RegularTopping(RegularToppingType.BELL_PEPPERS, false));
        addTopping(new RegularTopping(RegularToppingType.SPINACH, false));
        addTopping(new RegularTopping(RegularToppingType.OLIVES, false));
        addTopping(new RegularTopping(RegularToppingType.ONIONS, false));
        addTopping(new Sauce(SauceType.MARINARA, false));
    }

    /** Receipt header for Veggie. */
    @Override
    protected String getPizzaType() {
        return "VEGGIE";
    }
}