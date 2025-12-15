package org.example.services;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.example.models.HardwareProject;
import org.example.models.Project;
import org.example.models.SoftwareProject;
import org.example.utils.Util;
import org.example.utils.exceptions.ProjectNotFoundException;

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

        throw new Exception("Project list is full");
    }

    public Project getProjectById(String id) throws ProjectNotFoundException {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getId().equalsIgnoreCase(id)) {
                return projects[i];
            }
        }

        throw new ProjectNotFoundException("Project not found");
    }

    public Project[] getAllProjects() {
        return Arrays.copyOf(projects, projectCount);
    }

    public int getProjectCount() {
        return projectCount;
    }

    public void displayProject(Project project) {
        int taskCount = project.getTaskCount();
        var tasks = project.getTasks();

        Util.displayText(
                String.format("Project Name: %s\nType: %s\nTeam Size: %d\nBudget: $%.2f\n",
                        project.getName(),
                        project.getType(),
                        project.getTeamSize(),
                        project.getBudget()));

        Util.displayText("Associated Tasks:");

        if (taskCount <= 0) {
            Util.displayText("No task added yet. Added tasks will display here.");
        } else {
            final int ROW_WIDTH = 52;

            Util.displayTableHeader(
                    ROW_WIDTH,
                    "| %-4s | %-20s | %-20s |",
                    "ID",
                    "TASK NAME",
                    "STATUS");

            IntStream
                    .range(0, taskCount)
                    .forEach(i -> {
                        var task = tasks[i];

                        Util.displayTableRow(
                                ROW_WIDTH,
                                "| %-4s | %-20s | %-20s |",
                                task.getId(),
                                task.getName(),
                                task.getStatus());
                    });
        }

        System.out.printf("\nCompletion Rate: %.2f%%\n", project.calculateCompletionRate());
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

    public void displayProjectDetails(Project project) throws ProjectNotFoundException {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].equals(project)) {
//                projects[i].displayProject();
                displayProject(projects[i]);
                return;
            }
        }

        throw new ProjectNotFoundException("Project not found");
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
