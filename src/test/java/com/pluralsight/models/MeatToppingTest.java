package com.pluralsight.models;

import com.pluralsight.enums.Meat;
import com.pluralsight.enums.Size;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class MeatToppingTest {

    @Test
    public void getPrice_should_addSurchargeWhenExtra() {
        // arrange
        MeatTopping extraPepperoni = new MeatTopping(Meat.PEPPERONI, true);
        Size size = Size.MEDIUM_12;
        BigDecimal expectedPrice = new BigDecimal("3.00");

        // act
        BigDecimal actualPrice = extraPepperoni.getPrice(size);

        // assert
        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }
}