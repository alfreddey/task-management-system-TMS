package org.example.interfaces.repositories;

import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.repositories.ListBasedTaskRepository;
import org.example.utils.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class
TaskRepositoryTest {

    private TaskRepository repo;

    @BeforeEach
    void setUp() {
        repo = new ListBasedTaskRepository();
    }

    @Test
    void testAddAndSize() {
        Task task = new Task("Task1", TaskStatus.PENDING, "P001");
        repo.add(task);
        assertEquals(1, repo.size());
    }

    @Test
    void testGetById() throws TaskNotFoundException {
        Task task = new Task("Task2", TaskStatus.IN_PROGRESS, "P001");
        repo.add(task);

        Task retrieved = repo.getById(task.getId());
        assertEquals(task.getName(), retrieved.getName());
        assertEquals(task.getId(), retrieved.getId());
    }

    @Test
    void testGetWithPredicate() {
        Task task = new Task("Task3", TaskStatus.PENDING, "P001");
        repo.add(task);

        Task retrieved = repo.get(t -> t.getName().equals("Task3"));
        assertEquals(task.getId(), retrieved.getId());
    }

    @Test
    void testGetByIdThrowsExceptionIfNotFound() {
        assertThrows(TaskNotFoundException.class, () -> repo.getById("NON_EXISTENT"));
    }

    @Test
    void testGetPredicateReturnsNullIfNotFound() {
        Task result = repo.get(t -> t.getName().equals("NON_EXISTENT"));
        assertNull(result, "Expected get() to return null when no task matches the predicate");
    }


    @Test
    void testFilterReturnsMatchingTasks() throws TaskNotFoundException {
        Task t1 = new Task("Alpha", TaskStatus.PENDING, "P001");
        Task t2 = new Task("Beta", TaskStatus.IN_PROGRESS, "P001");
        repo.add(t1);
        repo.add(t2);

        TaskRepository filtered = repo.filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS);
        assertEquals(1, filtered.size());
        assertEquals("Beta", filtered.getById(t2.getId()).getName());
    }

    @Test
    void testUpdateTaskStatus() throws TaskNotFoundException {
        Task task = new Task("UpdateMe", TaskStatus.PENDING, "P001");
        repo.add(task);

        repo.updateTaskStatus(t -> t.getName().equals("UpdateMe"), TaskStatus.COMPLETED);
        Task updated = repo.getById(task.getId());
        assertEquals(TaskStatus.COMPLETED, updated.getStatus());
    }

    @Test
    void testUpdateTaskStatusThrowsIfNotFound() {
        assertThrows(TaskNotFoundException.class, () ->
                repo.updateTaskStatus(t -> t.getName().equals("NON_EXISTENT"), TaskStatus.COMPLETED)
        );
    }

    @Test
    void testRemoveTask() throws TaskNotFoundException {
        Task task = new Task("RemoveMe", TaskStatus.PENDING, "P001");
        repo.add(task);

        Task removed = repo.removeTask(t -> t.getName().equals("RemoveMe"));
        assertEquals(task.getId(), removed.getId());
        assertEquals(0, repo.size());
    }

    @Test
    void testRemoveTaskThrowsIfNotFound() {
        assertThrows(TaskNotFoundException.class, () ->
                repo.removeTask(t -> t.getName().equals("NON_EXISTENT"))
        );
    }

    @Test
    void testForEachIteration() {
        Task t1 = new Task("Iter1", TaskStatus.PENDING, "P001");
        Task t2 = new Task("Iter2", TaskStatus.IN_PROGRESS, "P001");
        repo.add(t1);
        repo.add(t2);

        AtomicBoolean found = new AtomicBoolean(false);
        repo.forEach(t -> {
            if (t.getName().equals("Iter1")) found.set(true);
        });

        assertTrue(found.get());
    }
}
