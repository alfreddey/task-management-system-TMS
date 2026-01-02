package org.example.utils;

import org.example.utils.exceptions.InvalidProjectIDException;
import org.example.utils.exceptions.InvalidTaskIDException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    @Test
    void testValidEmails() {
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("test@example.com"));
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("AL123@gmail.co"));
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("first.last+tag@domain.org"));
    }

    @Test
    void testInvalidEmails() {
        Exception e1 = assertThrows(Exception.class, () -> ValidationUtils.validateEmail("plainaddress"));
        assertTrue(e1.getMessage().contains("Invalid email format"));

        Exception e2 = assertThrows(Exception.class, () -> ValidationUtils.validateEmail("missing@domain"));
        assertTrue(e2.getMessage().contains("Invalid email format"));

        Exception e3 = assertThrows(Exception.class, () -> ValidationUtils.validateEmail("missing@.com"));
        assertTrue(e3.getMessage().contains("Invalid email format"));
    }

    @Test
    void testValidProjectIDs() {
        assertDoesNotThrow(() -> ValidationUtils.validateProjectID("P001"));
        assertDoesNotThrow(() -> ValidationUtils.validateProjectID("p123"));
        assertDoesNotThrow(() -> ValidationUtils.validateProjectID("P999"));
    }

    @Test
    void testInvalidProjectIDs() {
        InvalidProjectIDException e1 = assertThrows(InvalidProjectIDException.class,
                () -> ValidationUtils.validateProjectID("001"));
        assertTrue(e1.getMessage().contains("Invalid input"));

        InvalidProjectIDException e2 = assertThrows(InvalidProjectIDException.class,
                () -> ValidationUtils.validateProjectID("PX01"));
        assertTrue(e2.getMessage().contains("Invalid input"));

        InvalidProjectIDException e3 = assertThrows(InvalidProjectIDException.class,
                () -> ValidationUtils.validateProjectID("P1"));
        assertTrue(e3.getMessage().contains("Invalid input"));
    }

    @Test
    void testValidTaskIDs() {
        assertDoesNotThrow(() -> ValidationUtils.validateTaskID("T001"));
        assertDoesNotThrow(() -> ValidationUtils.validateTaskID("t123"));
        assertDoesNotThrow(() -> ValidationUtils.validateTaskID("T999"));
    }

    @Test
    void testInvalidTaskIDs() {
        InvalidTaskIDException e1 = assertThrows(InvalidTaskIDException.class,
                () -> ValidationUtils.validateTaskID("001"));
        assertTrue(e1.getMessage().contains("Invalid input"));

        InvalidTaskIDException e2 = assertThrows(InvalidTaskIDException.class,
                () -> ValidationUtils.validateTaskID("TX01"));
        assertTrue(e2.getMessage().contains("Invalid input"));

        InvalidTaskIDException e3 = assertThrows(InvalidTaskIDException.class,
                () -> ValidationUtils.validateTaskID("T1"));
        assertTrue(e3.getMessage().contains("Invalid input"));
    }
}
