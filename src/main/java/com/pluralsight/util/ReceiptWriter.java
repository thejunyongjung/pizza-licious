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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            bw.write(center("PIZZA-licious Receipt", LINE_LENGTH));
            bw.newLine();
            bw.write(center(now.format(DISPLAY_FORMAT), LINE_LENGTH));
            bw.newLine();
            bw.write("=".repeat(LINE_LENGTH));
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
            writeTotalLine(bw, order.getTotal());

        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }

    /** Writes one item with price on the first line. */
    private void writeItem(BufferedWriter bw, OrderItem item) throws IOException {
        String priceString = String.format("$%.2f", item.getPrice());
        int maxLineLength = LINE_LENGTH - priceString.length() - 1;

        List<String> lines = new ArrayList<>();
        for (String line : item.getDescription()) {
            lines.addAll(Arrays.asList(splitIntoLines(line, maxLineLength)));
        }

        // First line + price right-aligned
        String firstLine = lines.get(0);
        int spaces = LINE_LENGTH - firstLine.length() - priceString.length();
        if (spaces < 1) spaces = 1;
        bw.write(firstLine + " ".repeat(spaces) + priceString);
        bw.newLine();

        // Remaining lines as-is
        for (int i = 1; i < lines.size(); i++) {
            bw.write(lines.get(i));
            bw.newLine();
        }
    }

    /** Writes the TOTAL line. */
    private void writeTotalLine(BufferedWriter bw, BigDecimal total) throws IOException {
        String priceString = String.format("$%.2f", total);
        int spaces = LINE_LENGTH - "TOTAL".length() - priceString.length();
        bw.write("TOTAL" + " ".repeat(spaces) + priceString);
        bw.newLine();
    }

    /** Splits long text into shorter lines without breaking words. */
    private String[] splitIntoLines(String text, int maxLineLength) {
        // Short text? Return as-is so leading spaces stay.
        if (text.length() <= maxLineLength) {
            return new String[] { text };
        }

        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();
        for (String word : words) {
            int spaceBefore = !current.isEmpty() ? 1 : 0;
            if (current.length() + spaceBefore + word.length() > maxLineLength && !current.isEmpty()) {
                lines.add(current.toString());
                current = new StringBuilder();
            }
            if (!current.isEmpty()) current.append(" ");
            current.append(word);
        }
        if (!current.isEmpty()) lines.add(current.toString());
        return lines.toArray(new String[0]);
    }

    /** Adds left padding so the text sits centered. */
    private String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        if (pad < 0) return text;
        return " ".repeat(pad) + text;
    }
}