package org.example.models;

import org.example.interfaces.repositories.TaskRepository;

public class HardwareProject extends Project {
    private double materialCost;

    public HardwareProject(String name, String description, int teamSize, double budget, double materialCost, TaskRepository tasks) {
        super(name, description, teamSize, budget, ProjectType.HARDWARE, tasks);
        this.materialCost = materialCost;
    }

    public HardwareProject(String name, String description, int teamSize, double budget, TaskRepository tasks) {
        this(name, description, teamSize, budget, 0, tasks);
    }
}
