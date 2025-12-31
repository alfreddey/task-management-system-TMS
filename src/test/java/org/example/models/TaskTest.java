package org.example.models;

import org.example.services.ProjectService;
import org.example.services.TaskServiceV0;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    ProjectService projectService;
    TaskServiceV0 taskService;
    Project softwareProject;
    Task task;

    @BeforeEach
    void setUp() {
        projectService = ProjectService.getService();
        taskService = TaskServiceV0.getService();

        softwareProject = new SoftwareProject("Software PR", "description", 12, 34.4);

        task = new Task(
                "Task Name 1",
                TaskStatus.COMPLETED,
                "P001"
        );

        taskService.addTaskToProject(softwareProject, task);
    }

    @Test
    void getStatus() {
        assertEquals(TaskStatus.COMPLETED, task.getStatus());

        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        task.setStatus(TaskStatus.PENDING);
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    void isCompleted() {
        assertTrue(task.isCompleted());

        task.setStatus(TaskStatus.IN_PROGRESS);
        assertFalse(task.isCompleted());
    }
}