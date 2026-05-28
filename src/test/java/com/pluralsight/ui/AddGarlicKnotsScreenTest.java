package com.pluralsight.ui;

import com.pluralsight.models.GarlicKnots;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class AddGarlicKnotsScreenTest {

    @Test
    public void getKnots_should_returnKnotsAsPromptedQuantity() {
        // arrange
        Scanner fakeInput = new Scanner("3\n");
        AddGarlicKnotsScreen screen = new AddGarlicKnotsScreen(fakeInput);
        int expectedQuantity = 3;

        // act
        GarlicKnots actualKnots = screen.getKnots();

        // assert
        assertNotNull(actualKnots);
        assertEquals(expectedQuantity, actualKnots.getQuantity());
    }
}