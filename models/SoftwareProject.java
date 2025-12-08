package models;

import java.util.HashMap;

public class SoftwareProject extends Project {
    public SoftwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget, ProjectType.SOFTWARE);
    }

    public HashMap<String, String> getProjectDetails() {
        return new HashMap<>();
    };
}