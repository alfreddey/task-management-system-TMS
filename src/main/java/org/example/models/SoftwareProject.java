package org.example.models;

import org.example.interfaces.repositories.TaskRepository;

public class SoftwareProject extends Project {
    public SoftwareProject(String name, String description, int teamSize, double budget, TaskRepository tasks) {
        super(name, description, teamSize, budget, ProjectType.SOFTWARE, tasks);
    }
}