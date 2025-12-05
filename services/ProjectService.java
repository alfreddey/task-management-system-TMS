package services;

import java.util.Arrays;
import java.util.stream.IntStream;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import utils.Util;

public class ProjectService {
    private final int MAX_PROJECTS = 999;
    private static ProjectService service;
    private Project[] projects;
    private int projectCount;

    private ProjectService() {
        this.projectCount = 0;
        this.projects = new Project[MAX_PROJECTS];
    }

    public static ProjectService getService() {
        if (service == null) {
            service = new ProjectService();
        }

        return service;
    }

    public void addProject(Project project) throws Exception {
        if (projectCount < MAX_PROJECTS) {
            projects[projectCount++] = project;
            return;
        }

        throw new Exception("Project array is full");
    }

    public Project getProjectById(String id) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getId().equals(id)) {
                return projects[i];
            }
        }

        return null;
    }

    public Project[] getAllProjects() {
        return Arrays.copyOf(projects, projectCount);
    }

    public int getProjectCount() {
        return projectCount;
    }

    public void displayAllProjects() {
        final int ROW_WIDTH = 132;
        Util.displayTableHeader(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                "ID",
                "PROJECT NAME",
                "TYPE",
                "TEAM SIZE",
                "BUDGET",
                "DESCRIPTION");

        IntStream
                .range(0, projectCount)
                .forEach(i -> {
                    var project = projects[i];

                    Util.displayTableRow(
                            ROW_WIDTH,
                            "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                            project.getId(),
                            project.getName(),
                            project.getType(),
                            project.getTeamSize(),
                            project.getBudget(),
                            project.getDescription());
                });
    }

    public void displayProjectDetails(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].equals(project)) {
                projects[i].displayProject();
                return;
            }
        }

        System.out.println("Project Not found");
    }

    public void addSoftwareProject(String name,
            String description,
            int teamSize,
            double budget) throws Exception {
        addProject(new SoftwareProject(name, description, teamSize, budget));
    }

    public void addHardwareProject(String name,
            String description,
            int teamSize,
            double budget,
            double materialCost) throws Exception {
        addProject(new HardwareProject(name, description, teamSize, budget, materialCost));
    }
}
