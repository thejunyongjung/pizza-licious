package com.pluralsight.models;

public enum CrustType {
    THIN ("Thin"),
    REGULAR ("Regular"),
    THICK ("Thick"),
    CAULIFLOWER ("Cauliflower");

    private final String displayName;

    CrustType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    @Override
    public String toString() { return displayName; }
}
