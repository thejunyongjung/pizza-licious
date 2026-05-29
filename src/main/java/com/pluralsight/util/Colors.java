package com.pluralsight.util;

/** ANSI color helpers for CLI Application output. */
public class Colors {
    public static final String RESET = "\u001B[0m";

    // Styles
    public static final String BOLD = "\u001B[1m";
    public static final String DIM  = "\u001B[2m";

    // Standard colors
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String CYAN    = "\u001B[36m";
    public static final String MAGENTA = "\u001B[35m";

    // Bright colors
    public static final String BRIGHT_CYAN  = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Helpers
    public static String red(String t)     { return RED + t + RESET; }
    public static String green(String t)   { return GREEN + t + RESET; }
    public static String yellow(String t)  { return YELLOW + t + RESET; }
    public static String cyan(String t)    { return CYAN + t + RESET; }
    public static String magenta(String t) { return MAGENTA + t + RESET; }
    public static String dim(String t)     { return DIM + t + RESET; }
    public static String bold(String t)    { return BOLD + t + RESET; }
    public static String brightCyan(String t) { return BRIGHT_CYAN + t + RESET; }

    public static String boldRed(String t)         { return BOLD + RED + t + RESET; }
    public static String boldCyan(String t)        { return BOLD + CYAN + t + RESET; }
    public static String boldYellow(String t)      { return BOLD + YELLOW + t + RESET; }
    public static String boldMagenta(String t)     { return BOLD + MAGENTA + t + RESET; }
    public static String boldBrightWhite(String t) { return BOLD + BRIGHT_WHITE + t + RESET; }
}