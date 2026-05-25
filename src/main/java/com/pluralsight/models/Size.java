package com.pluralsight.models;

public enum Size {
    PERSONAL_8 ("Personal 8\"", 8.50),
    MEDIUM_12 ("Medium 12\"", 12.00),
    LARGE_16 ("LARGE 16\"", 16.50);

    private final String displayName;
    private final double basePrice;

    Size(String displayName, double basePrice) {
        this.displayName = displayName;
        this.basePrice = basePrice;
    }

    public String getDisplayName() { return displayName; }

    public double getBasePrice() { return basePrice; }

    @Override
    public String toString() { return displayName; }
}
