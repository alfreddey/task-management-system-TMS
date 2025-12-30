package org.example.repositories;

import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.TaskRepository;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListBasedTaskRepository implements TaskRepository, UnaryIterable<Task> {
    private final List<Task> tasks;

    public ListBasedTaskRepository(List<Task> tasks) {
        this.tasks = tasks;
    }

    public ListBasedTaskRepository() { this.tasks = new ArrayList<>(); }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    public int size() {
        return tasks.size();
    }

    @Override
    public Task get(Predicate<Task> condition) {
        return tasks.stream()
                .filter(condition)
                .findFirst()
                .orElse(null);
    }

    public Task getById(String id) throws TaskNotFoundException {
        return tasks.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public TaskRepository filter(Predicate<Task> condition) {
        var filteredTasks = tasks.stream()
                .filter(condition)
                .toList();

        return new ListBasedTaskRepository(new ArrayList<>(filteredTasks));
    }

    @Override
    public Task updateTaskStatus(Predicate<Task> condition, TaskStatus status) throws TaskNotFoundException {
        var task = get(condition);

        task.setStatus(status);

        return task;
    }

    @Override
    public Task removeTask(Predicate<Task> condition) throws TaskNotFoundException {
        var task = tasks.stream()
                .filter(condition)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task to be deleted not found"));

        tasks.remove(task);

        return task;
    }

    @Override
    public void forEach(Consumer<Task> action) {
        tasks.forEach(action);
    }
}
