package com.pluralsight.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class MargheritaTest {

    @Test
    public void getPrice_should_returnFixedSignaturePrice() {
        // arrange
        Margherita margherita = new Margherita();
        BigDecimal expectedPrice = new BigDecimal("13.50");

        // act
        BigDecimal actualPrice = margherita.getPrice();

        // assert
        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }
}