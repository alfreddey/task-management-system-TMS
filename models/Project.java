package models;

import java.util.Arrays;
import java.util.HashMap;

import utils.exceptions.TaskNotFoundException;

public abstract class Project {
    private static final int MAX_TASKS = 999;
    private static int index = 0;
    private int teamSize;
    private int taskCount;
    private double budget;
    private double completionRate;
    protected String id;
    protected String name;
    protected String description;
    private Task[] tasks;
    private ProjectType type;

    public Project(String name, String description, int teamSize, double budget, ProjectType type) {
        index += 1;
        this.id = String.format("P%03d", index);
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        this.taskCount = 0;
        this.completionRate = 0;
        this.tasks = new Task[MAX_TASKS];
        this.type = type;
    }

    public abstract HashMap<String, String> getProjectDetails();

    public void displayProject() {
        System.out.printf("Project Name: %s\nType: %s\nTeam Size: %d\nBudget: $%.2f\n",
                this.name,
                this.type,
                this.teamSize,
                this.budget);

        System.out.println("\nAssociated Tasks:");

        if (taskCount <= 0) {
            System.out.println("No task added yet. Added tasks will display here.");
        } else {
            // Display header
            System.out.println("-".repeat(54));
            System.out.printf("| %-4s | %-20s | %-20s |\n",
                    "ID",
                    "TASK NAME",
                    "STATUS");
            System.out.println("-".repeat(54));

            // Display rows
            for (int i = 0; i < taskCount; i++) {
                System.out.printf("| %-4s | %-20s | %-20s |\n",
                        tasks[i].getId(),
                        tasks[i].getName(),
                        tasks[i].getStatus());
                System.out.println("-".repeat(54));
            }
        }

        System.out.printf("\nCompletion Rate: %.2f%%\n", this.getCompletionRate());
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

    // Getters
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

    public double getCompletionRate() {
        double count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getStatus().equals(TaskStatus.COMPLETED)) {
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