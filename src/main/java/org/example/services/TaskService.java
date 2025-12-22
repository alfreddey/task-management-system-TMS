package org.example.services;

import org.example.models.Project;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.TaskNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
//        Task[] tasks = project.getTaskArray();
//        int taskCount = project.getTaskCount();
//        int MAX_TASKS = project.getMaxTasks();
//        if (taskCount >= MAX_TASKS - 1) {
//            System.out.println("Task array is full");
//            return;
//        }
//
//        tasks[taskCount] = task;
//        project.setTaskCount(taskCount + 1);
        project.getTasks().add(task);
    }

  /**
   * Retrieves a task from a project based on its name (case-insensitive).
   *
   * @param project The project to search within.
   * @param taskName The name of the task to find.
   * @return The {@code Task} object if found, otherwise {@code null}.
   */
    public Task getTaskByName(Project project, String taskName) {
        return project.getTasks().stream()
                .filter((task) -> task.getName().equalsIgnoreCase(taskName))
                .findAny()
                .orElse(null);
//        var tasks = project.getTasks();
//        for (Task task : tasks) {
//            if (task.getName().equalsIgnoreCase(taskName)) {
//                return task;
//            }
//        }
//
//        return null;
    }

    /**
     * Searches for a specific task within a project using its unique identifier.
     * <p>
     * The search is case-insensitive. This method iterates through the underlying
     * array of tasks associated with the project.
     *
     * @param project the project containing the tasks to be searched
     * @param id      the unique identifier of the task to retrieve
     * @return the {@code Task} matching the provided ID
     * @throws TaskNotFoundException if no task with the specified ID exists in the project
     */
    public Task getTaskById(Project project, String id) throws TaskNotFoundException {
//        int taskCount = project.getTaskCount();
//        Task[] tasks = project.getTasks();
//        for (int i = 0; i < taskCount; i++) {
//            if (tasks[i].getId().equalsIgnoreCase(id)) {
//                return tasks[i];
//            }
//        }
//
//        throw new TaskNotFoundException("Task not found");
        return project.getTasks().stream()
                .filter((task) -> task.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
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
//      final int MAX_TASKS = project.getMaxTasks();
//      final int taskCount = project.getTaskCount();
//      Task[] tasks = project.getTaskArray();
//      Task[] newArray = new Task[MAX_TASKS];
//
//      Task taskTarget = null;
//      int index = 0;
//      for (int i = 0; i < taskCount; i++) {
//        Task task = tasks[i];
//
//        if (!task.getId().equalsIgnoreCase(taskId)) {
//          newArray[index++] = task;
//        } else {
//          taskTarget = task;
//        }
//      }
//
//      if (taskTarget == null) {
//        throw new TaskNotFoundException("Task to be removed not found");
//      }
//
//      project.setTaskCount(taskCount - 1);
//      System.arraycopy(newArray, 0, tasks, 0, taskCount);
//
//      return taskTarget;
        List<Task> tasks = project.getTasks();

        Task taskTarget =
                tasks.stream()
                        .filter((task) -> task.getId().equalsIgnoreCase(taskId))
                        .findFirst()
                        .orElseThrow(() -> new TaskNotFoundException("Task to be removed not found"));

        tasks.remove(taskTarget);

        return taskTarget;
    }

    /**
     * Calculates the completion percentage of a project based on the status of its tasks.
     * <p>
     * The rate is determined by the ratio of completed tasks to the total number of tasks.
     * If the project contains no tasks, the completion rate is returned as 0.0.
     *
     * @param project the project for which to calculate the completion rate
     * @return the percentage of completed tasks, ranging from 0.0 to 100.0
     */
    public double calculateCompletionRate(Project project) {
//        int taskCount = project.getTaskCount();
//        Task[] tasks = project.getTasks();
//        double count = 0;
//        for (int i = 0; i < taskCount; i++) {
//            if (tasks[i].isCompleted()) {
//                count++;
//            }
//        }
//
//        return (taskCount > 0) ? (count / taskCount) * 100 : 0;
        List<Task> tasks = project.getTasks();

        double completedTaskCount = (double) tasks.stream()
                .filter(Task::isCompleted)
                .count();

        return !tasks.isEmpty() ? completedTaskCount / tasks.size() * 100 : 0;
    }
}
