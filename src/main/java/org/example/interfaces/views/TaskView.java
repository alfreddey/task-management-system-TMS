package org.example.interfaces.views;

import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.TaskRepository;
import org.example.models.Task;

public interface TaskView {
    void viewTasks(TaskRepository tasks);
    void viewTask(Task task);
}
