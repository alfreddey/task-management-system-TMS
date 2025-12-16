package org.example.services;

import org.example.models.Project;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.TaskNotFoundException;

/**
 * Provides a set of services for managing tasks within projects, including
 * creation, status updates, and association with projects. This class
 * follows the Singleton pattern.
 */
public class TaskService {
    private static TaskService service;

  /**
   * Returns the singleton instance of the TaskService.
   * If the instance does not exist, it creates one.
   *
   * @return The single instance of TaskService.
   */
    public static TaskService getService() {
        if (service == null) {
            service = new TaskService();
        }

        return service;
    }

  /**
   * Updates the status of a specific task.
   *
   * @param task The task object to be updated.
   * @param status The new {@code TaskStatus} to set for the task.
   */
    public void updateTaskStatus(Task task, TaskStatus status) {
        task.setStatus(status);
    }

  /**
   * Creates a new {@code Task} instance with the specified details.
   *
   * @param name The name of the new task.
   * @param status The initial status of the new task.
   * @param projectId The ID of the project to which this task belongs.
   * @return The newly created {@code Task} object.
   */
    public Task createTask(String name, TaskStatus status, String projectId) {
        return new Task(name, status, projectId);
    }

  /**
   * Adds an existing task to a specified project.
   *
   * @param project The project to which the task should be added.
   * @param task The task to be added.
   */
    public void addTaskToProject(Project project, Task task) {
        project.addTask(task);
    }

  /**
   * Retrieves a task from a project based on its name (case-insensitive).
   *
   * @param project The project to search within.
   * @param taskName The name of the task to find.
   * @return The {@code Task} object if found, otherwise {@code null}.
   */
    public Task getTaskByName(Project project, String taskName) {
        var tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                return task;
            }
        }

        return null;
    }

  /**
   * Removes a task from a project using the task's ID.
   *
   * @param project The project from which the task should be removed.
   * @param taskId The ID of the task to remove.
   * @return The removed {@code Task} object.
   * @throws TaskNotFoundException if no task with the given ID is found in the project.
   */
    public Task removeTaskById(Project project, String taskId) throws TaskNotFoundException {
        return project.removeTaskById(taskId);
    }
}
