package org.example.models;

public class SoftwareProject extends Project {
    public SoftwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget, ProjectType.SOFTWARE);
    }
}