package org.example.models;

import org.example.services.ProjectService;
import org.example.services.TaskServiceV0;
import org.example.utils.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    ProjectService projectService;
    TaskServiceV0 taskService;
    Project softwareProject;
    Project hardwareProject;

    @DisplayName("Create new software and hardware projects")
    @BeforeEach
    void setUp() {
        projectService = ProjectService.getService();
        taskService = TaskServiceV0.getService();

        softwareProject = new SoftwareProject("Software PR", "description", 12, 34.4);
        hardwareProject = new HardwareProject("Hardware PR", "desc", 34, 09.23, 23);

        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "Task Name 1",
                        TaskStatus.COMPLETED,
                        "P001"
                ));
    }

    @DisplayName("Test for task addition")
    @Test
    void testAddTask() throws Exception {
        assertEquals("T001", taskService.getTaskById(softwareProject, "T001").getId());
    }

    @Test
    void testTaskNotFoundException() {
        Exception caughtException = assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(softwareProject, "T990");
        });

        assertEquals("Task not found", caughtException.getMessage());
    }

    @Test
    void testGetCompletionRate() {
        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.COMPLETED,
                        "P001"));

        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.PENDING,
                        "P001"
                )
        );

        var completionRate = taskService.calculateCompletionRate(softwareProject);
        assertEquals(100 * (2 / 3.0), completionRate);
    }

    @DisplayName("Test for the case where one task is IN_PROGRESS")
    @Test
    void testGetCompletionRateOneInProgressCase() {
        // Add two more tasks to softwareProject
        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.COMPLETED,
                        "P001"
                )
        );

        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.IN_PROGRESS,
                        "P001"
                )
        );

        var completionRate = taskService.calculateCompletionRate(softwareProject);
        assertEquals(100 * (2 / 3.0), completionRate);
    }

    @DisplayName("Test for the case where all tasks a completed")
    @Test
    void testGetCompletionRateAllCompletedCase() {
        // Add two more tasks to softwareProject
        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.COMPLETED,
                        "P001"
                )
        );

        taskService.addTaskToProject(
                softwareProject,
                new Task(
                        "adf",
                        TaskStatus.COMPLETED,
                        "P001"
                )
        );

        var completionRate = taskService.calculateCompletionRate(softwareProject);
        assertEquals(100, completionRate);
    }

    @Test
    void testRemoveTaskById() throws Exception {
        Task task = new Task(
                "delete me",
                TaskStatus.COMPLETED,
                "P001"
        );

        taskService.addTaskToProject(softwareProject, task);

        String taskId = task.getId();
        taskService.removeTaskById(softwareProject, taskId);

        // Check if truly removed
        Exception caughtException = assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(softwareProject, taskId);
        });

        assertEquals("Task not found", caughtException.getMessage());
    }
}