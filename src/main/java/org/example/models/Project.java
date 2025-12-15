package org.example.models;

import java.util.Arrays;
import org.example.utils.exceptions.TaskNotFoundException;

public abstract class Project {
    private static final int MAX_TASKS = 999;
    private static int index = 0;
    private final int teamSize;
    private int taskCount;
    private final double budget;
    protected String id;
    protected String name;
    protected String description;
    private final Task[] tasks;
    private final ProjectType type;

    public Project(String name, String description, int teamSize, double budget, ProjectType type) {
        index += 1;
        this.id = String.format("P%03d", index);
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        this.taskCount = 0;
        this.tasks = new Task[MAX_TASKS];
        this.type = type;
    }

    public Task getTaskById(String id) throws TaskNotFoundException {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equalsIgnoreCase(id)) {
                return tasks[i];
            }
        }

        throw new TaskNotFoundException("Task not found");
    }

    public void addTask(Task task) {
        if (taskCount >= MAX_TASKS - 1) {
            System.out.println("Task array is full");
            return;
        }

        tasks[taskCount++] = task;
    }

    public Task[] getTasks() {
        return Arrays.copyOf(tasks, taskCount);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public String getDescription() {
        return description;
    }

    public double getBudget() {
        return budget;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public double calculateCompletionRate() {
        double count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].isCompleted()) {
                count++;
            }
        }

        return (taskCount > 0) ? (count / taskCount) * 100 : 0;
    }

    public ProjectType getType() {
        return type;
    }

    public Task removeTaskById(String taskId) throws TaskNotFoundException {
        Task[] newArray = new Task[MAX_TASKS];

        Task taskTarget = null;
        int index = 0;
        for (int i = 0; i < taskCount; i++) {
            Task task = tasks[i];

            if (!task.getId().equalsIgnoreCase(taskId)) {
                newArray[index++] = task;
            } else {
                taskTarget = task;
            }
        }

        if (taskTarget == null) {
            throw new TaskNotFoundException("Task to be removed not found");
        }

        taskCount -= 1;
        System.arraycopy(newArray, 0, tasks, 0, MAX_TASKS);

        return taskTarget;
    }
}