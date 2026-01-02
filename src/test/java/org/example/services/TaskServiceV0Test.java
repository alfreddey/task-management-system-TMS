package org.example.services;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.repositories.TaskRepository;
import org.example.interfaces.views.TaskView;
import org.example.models.Project;
import org.example.models.SoftwareProject;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.utils.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceV0Test {

    private ProjectRepository<Project> mockProjectRepo;
    private TaskView mockTaskView;
    private TaskRepository mockTaskRepo;
    private TaskServiceV0<Project> taskService;
    private Project project;

    @BeforeEach
    void setUp() throws ProjectNotFoundException {
        mockProjectRepo = mock(ProjectRepository.class);
        mockTaskView = mock(TaskView.class);
        mockTaskRepo = mock(TaskRepository.class);
        taskService = new TaskServiceV0<>(mockProjectRepo, mockTaskView);

        project = new SoftwareProject("Proj1", "Desc", 3, 1000, mockTaskRepo);
        when(mockProjectRepo.getById("P001")).thenReturn(project);
    }

    @Test
    void testViewTasks() throws ProjectNotFoundException {
        taskService.viewTasks("P001");
        verify(mockTaskView, times(1)).viewTasks(mockTaskRepo);
    }

    @Test
    void testViewTasksThrowsIfProjectNotFound() throws ProjectNotFoundException {
        when(mockProjectRepo.getById("NON_EXISTENT")).thenThrow(new ProjectNotFoundException("Not found"));

        assertThrows(ProjectNotFoundException.class, () -> taskService.viewTasks("NON_EXISTENT"));
    }

    @Test
    void testAddNewTask() throws ProjectNotFoundException {
        Task task = new Task("Task1", TaskStatus.PENDING, "P001");
        taskService.addNewTask("P001", task);

        verify(mockTaskRepo, times(1)).add(task);
    }

    @Test
    void testAddNewTaskThrowsIfProjectNotFound() throws ProjectNotFoundException {
        Task task = new Task("Task2", TaskStatus.PENDING, "P002");
        when(mockProjectRepo.getById("NON_EXISTENT")).thenThrow(new ProjectNotFoundException("Not found"));

        assertThrows(ProjectNotFoundException.class, () -> taskService.addNewTask("NON_EXISTENT", task));
    }

    @Test
    void testUpdateTaskStatus() throws ProjectNotFoundException, TaskNotFoundException {
        taskService.updateTaskStatus("T001", "P001", TaskStatus.COMPLETED);

        verify(mockTaskRepo, times(1))
                .updateTaskStatus(any(), eq(TaskStatus.COMPLETED));
    }


    @Test
    void testUpdateTaskStatusThrowsIfProjectNotFound() throws ProjectNotFoundException {
        when(mockProjectRepo.getById("NON_EXISTENT")).thenThrow(new ProjectNotFoundException("Not found"));

        assertThrows(ProjectNotFoundException.class,
                () -> taskService.updateTaskStatus("T001", "NON_EXISTENT", TaskStatus.COMPLETED));
    }

    @Test
    void testRemoveTask() throws ProjectNotFoundException, TaskNotFoundException {
        Task task = new Task("TaskToRemove", TaskStatus.PENDING, "P001");
        when(mockTaskRepo.removeTask(any())).thenReturn(task);

        Task removed = taskService.removeTask("P001", "T001");
        assertEquals(task, removed);

        verify(mockTaskRepo, times(1)).removeTask(any());
    }

    @Test
    void testRemoveTaskThrowsIfProjectNotFound() throws ProjectNotFoundException {
        when(mockProjectRepo.getById("NON_EXISTENT")).thenThrow(new ProjectNotFoundException("Not found"));

        assertThrows(ProjectNotFoundException.class,
                () -> taskService.removeTask("NON_EXISTENT", "T001"));
    }

    @Test
    void testRemoveTaskThrowsIfTaskNotFound() throws ProjectNotFoundException, TaskNotFoundException {
        when(mockTaskRepo.removeTask(any())).thenThrow(new TaskNotFoundException("Task not found"));

        assertThrows(TaskNotFoundException.class,
                () -> taskService.removeTask("P001", "NON_EXISTENT"));
    }
}
