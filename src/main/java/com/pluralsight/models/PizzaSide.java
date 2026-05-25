package com.pluralsight.models;

public enum PizzaSide {
    RED_PEPPER("Red Pepper"),
    PARMESAN("Parmesan");

    private final String displayName;

    PizzaSide(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
