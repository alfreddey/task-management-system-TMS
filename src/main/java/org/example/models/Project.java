package org.example.models;

import org.example.interfaces.repositories.TaskRepository;

public abstract class Project {
    private static int index = 0;
    private final String id;
    private final String name;
    private final String description;
    private final int teamSize;
    private final double budget;
    private final ProjectType type;
    private final TaskRepository tasks;

    public Project(String name, String description, int teamSize, double budget, ProjectType type, TaskRepository tasks) {
        this.id = String.format("P%03d", ++index);
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
        this.budget = budget;
        this.type = type;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public double getBudget() {
        return budget;
    }

    public ProjectType getType() {
        return type;
    }

    public TaskRepository getTasks() {
        return tasks;
    }

    public String getId() {
        return id;
    }
}