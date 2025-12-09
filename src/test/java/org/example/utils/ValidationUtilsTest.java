package org.example.utils;

import org.example.models.Project;
import org.example.models.SoftwareProject;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.InvalidProjectIDException;
import org.example.utils.exceptions.InvalidTaskIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    Project project;
    Task task;

    @BeforeEach
    void setUp() {
        project = new SoftwareProject("My name", "snd", 12, 344.5);
        task = new Task("adfa", TaskStatus.IN_PROGRESS, "P001");
    }

    @Test
    void validateProjectID() {
        assertDoesNotThrow(() -> {
            ValidationUtils.validateProjectID("P001");
        });

        assertThrows(InvalidProjectIDException.class, () -> {
            ValidationUtils.validateProjectID("P00X");
        }, "Valid project ID was passed as an argument");
    }

    @Test
    void validateTaskID() {
        assertDoesNotThrow(() -> {
            ValidationUtils.validateTaskID("T001");
        });

        assertThrows(InvalidTaskIDException.class, () -> {
            ValidationUtils.validateTaskID("T00X");
        }, "Valid task ID was passed as an argument");
    }
}