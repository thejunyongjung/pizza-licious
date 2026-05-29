package com.pluralsight.enums;

public enum Meat {
    PEPPERONI ("Pepperoni"),
    SAUSAGE ("Sausage"),
    HAM ("Ham"),
    BACON ("Bacon"),
    CHICKEN ("Chicken"),
    MEATBALL ("Meatball");

    private String displayName;

    Meat(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
