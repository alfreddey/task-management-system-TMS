package org.example.models;

import java.util.Arrays;

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

    public Task[] getTasks() {
        return Arrays.copyOf(tasks, taskCount);
    }

    public Task[] getTaskArray() { return tasks; }

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

    public ProjectType getType() {
        return type;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getMaxTasks() { return MAX_TASKS; }
}