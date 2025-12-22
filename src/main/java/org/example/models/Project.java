package org.example.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Project {
    private static final int MAX_TASKS = 999;
    private static int index = 0;
    private final int teamSize;
    private final double budget;
    protected String id;
    protected String name;
    protected String description;
    private final List<Task> tasks;
    private final ProjectType type;

    public Project(String name, String description, int teamSize, double budget, ProjectType type) {
        index += 1;
        this.id = String.format("P%03d", index);
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        this.tasks = new ArrayList<>();
        this.type = type;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTaskCount() {
        return tasks.size();
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
}