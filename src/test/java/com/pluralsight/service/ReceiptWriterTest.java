package com.pluralsight.service;

import com.pluralsight.models.GarlicKnots;
import com.pluralsight.models.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class ReceiptWriterTest {

    @Test
    public void save_should_createReceiptFile() {
        // arrange
        Order order = new Order();
        order.addItem(new GarlicKnots(3));
        ReceiptWriter writer = new ReceiptWriter();
        File file = new File("receipts");
        int expectedCount = (file.exists() ? file.list().length : 0) + 1;

        // act
        writer.save(order);

        // assert
        int actualCount = file.list().length;
        assertEquals(expectedCount, actualCount);
    }
}