package com.pluralsight;

import com.pluralsight.ui.HomeScreen;
import com.pluralsight.util.Colors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printBanner();
        Colors.pause(700);   // ← let the welcome banner breathe

        Scanner scanner = new Scanner(System.in);
        HomeScreen homeScreen = new HomeScreen(scanner);
        homeScreen.display();

        scanner.close();
    }

    private static void printBanner() {
        System.out.println();
        System.out.println(Colors.BRIGHT_CYAN + "╔══════════════════════════════════════════════════════════╗" + Colors.RESET);
        System.out.println(Colors.BRIGHT_CYAN + "║" + Colors.BOLD + Colors.BRIGHT_WHITE
                + "                 Welcome to PIZZA-licious                 "
                + Colors.RESET + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BRIGHT_CYAN + "╚══════════════════════════════════════════════════════════╝" + Colors.RESET);
    }
}