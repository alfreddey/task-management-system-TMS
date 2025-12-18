package org.example.models;

public class HardwareProject extends Project {
    private final double materialCost;

    public HardwareProject(String name, String description, int teamSize, double budget, double materialCost) {
        super(name, description, teamSize, budget, ProjectType.HARDWARE);
        this.materialCost = materialCost;
    }

    public HardwareProject(String name, String description, int teamSize, double budget) {
        this(name, description, teamSize, budget, 0);
    }
}
