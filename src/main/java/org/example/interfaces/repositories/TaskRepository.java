package org.example.interfaces.repositories;

import org.example.interfaces.UnaryIterable;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.TaskNotFoundException;

import java.util.function.Predicate;

public interface TaskRepository extends UnaryIterable<Task> {
    int size();
    void add(Task task);
    Task get(Predicate<Task> condition);
    Task getById(String id) throws TaskNotFoundException;
    TaskRepository filter(Predicate<Task> condition);
    Task updateTaskStatus(Predicate<Task> condition, TaskStatus status) throws TaskNotFoundException;
    Task removeTask(Predicate<Task> condition) throws TaskNotFoundException;
}
