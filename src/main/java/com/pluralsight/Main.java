package com.pluralsight;

import com.pluralsight.ui.HomeScreen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printBanner();

        Scanner scanner = new Scanner(System.in);
        HomeScreen homeScreen = new HomeScreen(scanner);
        homeScreen.display();

        scanner.close();
    }

    private static void printBanner() {
        System.out.println();

        System.out.println("======================================");
        System.out.println("      Welcome to PIZZA-licious        ");
        System.out.println("======================================");
    }
}