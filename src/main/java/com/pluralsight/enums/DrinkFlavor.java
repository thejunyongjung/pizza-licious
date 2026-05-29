package com.pluralsight.enums;

public enum DrinkFlavor {
    COKE("Coke"),
    SPRITE("Sprite"),
    WATER("Water"),
    LEMONADE("Lemonade"),
    ICED_TEA("Iced Tea");

    private final String displayName;

    DrinkFlavor(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
