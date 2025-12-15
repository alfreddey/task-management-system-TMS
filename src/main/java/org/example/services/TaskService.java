package org.example.services;

import org.example.models.Project;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.TaskNotFoundException;

public class TaskService {
    private static TaskService service;

    public static TaskService getService() {
        if (service == null) {
            service = new TaskService();
        }

        return service;
    }

    public void updateTaskStatus(Task task, TaskStatus status) {
        task.setStatus(status);
    }

    public Task createTask(String name, TaskStatus status, String projectId) {
        return new Task(name, status, projectId);
    }

    public void addTaskToProject(Project project, Task task) {
        project.addTask(task);
    }

    public Task getTaskByName(Project project, String taskName) {
        var tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                return task;
            }
        }

        return null;
    }

    public Task removeTaskById(Project project, String taskId) throws TaskNotFoundException {
        return project.removeTaskById(taskId);
    }
}
