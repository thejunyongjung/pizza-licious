package com.pluralsight.enums;

public enum RegularToppingType {
    ONIONS("Onions"),
    MUSHROOMS("Mushrooms"),
    BELL_PEPPERS("Bell Peppers"),
    OLIVES("Olives"),
    TOMATOES("Tomatoes"),
    SPINACH("Spinach"),
    BASIL("Basil"),
    PINEAPPLE("Pineapple"),
    ANCHOVIES("Anchovies");

    private final String displayName;

    RegularToppingType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
