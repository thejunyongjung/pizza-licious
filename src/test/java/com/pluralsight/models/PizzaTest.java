package com.pluralsight.models;

import com.pluralsight.enums.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class PizzaTest {

    @Test
    public void getPrice_should_includeSizeAndAllToppings() {
        // arrange
        Pizza pizza = new Pizza(Size.MEDIUM_12, CrustType.THIN, false);
        pizza.addTopping(new MeatTopping(Meat.PEPPERONI, true));
        pizza.addTopping(new CheeseTopping(Cheese.MOZZARELLA, false));
        pizza.addTopping(new RegularTopping(RegularToppingType.BASIL, false));
        pizza.addTopping(new Sauce(SauceType.MARINARA, false));
        BigDecimal expectedPrice = new BigDecimal("16.50");

        // act
        BigDecimal actualPrice = pizza.getPrice();

        // assert
        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }
}
