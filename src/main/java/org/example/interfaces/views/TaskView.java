package org.example.interfaces.views;

import org.example.interfaces.UnaryIterable;
import org.example.models.Task;

public interface TaskView {
    void viewTasks(UnaryIterable<Task> tasks);
    void viewTask(Task task);
}
