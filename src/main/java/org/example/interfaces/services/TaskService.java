package org.example.interfaces.services;

import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.utils.exceptions.TaskNotFoundException;

public interface TaskService {
    void viewTasks(String projectId) throws ProjectNotFoundException;
    void updateTaskStatus(String taskId, String projectId, TaskStatus status) throws ProjectNotFoundException, TaskNotFoundException;
    void addNewTask(String projectId, Task task) throws ProjectNotFoundException;
    Task removeTask(String projectId, String taskId) throws ProjectNotFoundException, TaskNotFoundException;
}