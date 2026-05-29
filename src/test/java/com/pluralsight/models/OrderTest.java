package com.pluralsight.models;

import com.pluralsight.enums.CrustType;
import com.pluralsight.enums.DrinkFlavor;
import com.pluralsight.enums.DrinkSize;
import com.pluralsight.enums.Size;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class OrderTest {

    @Test
    public void getTotal_should_sumAcrossAllItemTypes() {
        // arrange
        Order order = new Order();
        order.addItem(new Pizza(Size.PERSONAL_8, CrustType.REGULAR, false));
        order.addItem(new Drink(DrinkSize.LARGE, DrinkFlavor.COKE));
        order.addItem(new GarlicKnots(3));
        BigDecimal expectedTotal = new BigDecimal("16.00");

        // act
        BigDecimal actualTotal = order.getTotal();

        // assert
        assertEquals(0, expectedTotal.compareTo(actualTotal));
    }
}