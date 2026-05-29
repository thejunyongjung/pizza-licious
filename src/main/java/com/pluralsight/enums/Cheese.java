package com.pluralsight.enums;

public enum Cheese {
    MOZZARELLA("Mozzarella"),
    PARMESAN("Parmesan"),
    RICOTTA("Ricotta"),
    GOAT("Goat"),
    BUFFALO("Buffalo");

    private final String displayName;

    Cheese(String displayName) {this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
