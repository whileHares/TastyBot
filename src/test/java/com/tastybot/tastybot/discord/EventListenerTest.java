package com.tastybot.tastybot.discord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EventListenerTest {

    @Test
    void testValidateDateInvalidDay() {
        // Arrange
        EventListener eventListener = mock(EventListener.class);

        // Act
        boolean result = eventListener.validateDate("40.01.2023");

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateDateInvalidMonth() {
        // Arrange
        EventListener eventListener = mock(EventListener.class);

        // Act
        boolean result = eventListener.validateDate("01.13.2023");

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateDateInvalidYear() {
        // Arrange
        EventListener eventListener = mock(EventListener.class);

        // Act
        boolean result = eventListener.validateDate("01.01.2021");

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateDateInvalidFormat() {
        // Arrange
        EventListener eventListener = mock(EventListener.class);

        // Act
        boolean result = eventListener.validateDate("2023-01-01");

        // Assert
        assertFalse(result);
    }
}

