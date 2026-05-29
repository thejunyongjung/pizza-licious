package com.pluralsight.service;

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
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm a");
    private static final int LINE_LENGTH = 60;

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
            bw.write(center("PIZZA-licious (Fremont, CA)", LINE_LENGTH));
            bw.newLine();
            bw.write(center(now.format(DISPLAY_FORMAT), LINE_LENGTH));
            bw.newLine();
            bw.write("=".repeat(LINE_LENGTH));
            bw.newLine();
            bw.write(center("Order ID: " + order.getOrderId(), LINE_LENGTH));
            bw.newLine();
            bw.newLine();

            // BODY
            for (OrderItem item : order.getItems()) {
                writeItem(bw, item);
                bw.newLine();
            }

            // FOOTER
            bw.write("-".repeat(LINE_LENGTH));
            bw.newLine();
            writeFooterLine(bw, "Subtotal",     order.getSubtotal());
            writeFooterLine(bw, "Tax (10.25%)", order.getTax());
            writeFooterLine(bw, "TOTAL",        order.getGrandTotal());

        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }

    /** Writes one item with price on the first line. */
    private void writeItem(BufferedWriter bw, OrderItem item) throws IOException {
        String priceString = String.format("$%.2f", item.getPrice());
        String[] lines = item.getDescription();

        // First line + price right-aligned
        String firstLine = lines[0];
        int spaces = LINE_LENGTH - firstLine.length() - priceString.length();
        if (spaces < 1) spaces = 1;
        bw.write(firstLine + " ".repeat(spaces) + priceString);
        bw.newLine();

        // Remaining lines as-is
        for (int i = 1; i < lines.length; i++) {
            bw.write(lines[i]);
            bw.newLine();
        }
    }

    /** Writes a footer line (label + right-aligned amount). */
    private void writeFooterLine(BufferedWriter bw, String label, BigDecimal amount) throws IOException {
        String priceStr = "$" + String.format("%.2f", amount);
        int spaces = LINE_LENGTH - label.length() - priceStr.length();
        if (spaces < 1) spaces = 1;
        bw.write(label + " ".repeat(spaces) + priceStr);
        bw.newLine();
    }

    /** Adds left padding so the text sits centered. */
    private String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        if (pad < 0) return text;
        return " ".repeat(pad) + text;
    }
}