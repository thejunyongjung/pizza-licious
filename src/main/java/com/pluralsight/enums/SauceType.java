package com.pluralsight.enums;

public enum SauceType {
    MARINARA("Marinara"),
    ALFREDO("Alfredo"),
    PESTO("Pesto"),
    BBQ("BBQ"),
    BUFFALO("Buffalo"),
    OLIVE_OIL("Olive Oil");

    private final String displayName;

    SauceType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}