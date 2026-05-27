package com.pluralsight.util;

import com.pluralsight.models.Order;
import com.pluralsight.models.OrderItem;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptWriter {
    // STATIC CONSTANTS
    private static final String RECEIPTS_DIRECTORY = "receipts";
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a");
    private static final int LINE_LENGTH = 50;

    /** Writes order details to receipts/yyyyMMdd-HHmmss.txt. */
    public void save(Order order) {
        File file = new File(RECEIPTS_DIRECTORY);
        // Checking whether the folder exists
        if (!file.exists()) {
            file.mkdirs();
        }

        // Filename with timestamp
        LocalDateTime now = LocalDateTime.now();
        String fileName = RECEIPTS_DIRECTORY + "/" + now.format(FILE_FORMAT) + ".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            // HEADER
            bw.write("PIZZA-licious Receipt");
            bw.newLine();
            bw.write(now.format(DISPLAY_FORMAT));
            bw.newLine();
            bw.write("=".repeat(LINE_LENGTH));
            bw.newLine();
            bw.newLine();

            // BODY
            for (OrderItem item : order.getItems()) {
                bw.write(formatLine(item.getDescription(), item.getPrice()));
                bw.newLine();
            }

            // FOOTER
            bw.newLine();
            bw.write("-".repeat(LINE_LENGTH));
            bw.newLine();
            bw.write(formatLine("TOTAL", order.getTotal()));
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }

    /** Pads description with spaces so the price right-aligns at LINE_LENGTH. */
    private String formatLine(String description, BigDecimal price) {
        String priceString = String.format("$%.2f", price);
        int spaces = Math.max(1, LINE_LENGTH - description.length() - priceString.length());
        return description + " ".repeat(spaces) + priceString;
    }
}
