package org.example.services;

import java.util.*;

import org.example.models.HardwareProject;
import org.example.models.Project;
import org.example.models.SoftwareProject;
import org.example.utils.Util;
import org.example.utils.exceptions.ProjectNotFoundException;

/**
 * Manages operations related to projects, including adding, retrieving, and displaying
 * project information. This class implements the Singleton pattern to ensure
 * only one instance of the service exists.
 */
public class ProjectService {
    private static ProjectService service;
    private static TaskService taskService;
    private final Map<String, Project> projects;

    /**
     * Private constructor to prevent direct instantiation, enforcing the Singleton pattern.
     * Initializes the project array and sets the initial project count to zero.
     */
    private ProjectService() {
        this.projects = new HashMap<>();
    }

    /**
     * Returns the singleton instance of the ProjectService.
     * If the instance does not exist, it creates one.
     *
     * @return The single instance of ProjectService.
     */
    public static ProjectService getService() {
        if (service == null) {
            service = new ProjectService();
            taskService = TaskService.getService();
        }

        return service;
    }

    /**
     * Adds a new project to the service's project list.
     *
     * @param project The project to add.
     */
    public void addProject(Project project) {
        projects.putIfAbsent(project.getId(), project);
    }

    /**
     * Retrieves a project by its unique ID.
     *
     * @param id The ID of the project to retrieve.
     * @return The Project object with the specified ID.
     * @throws ProjectNotFoundException if no project with the given ID is found.
     */
    public Project getProjectById(String id) throws ProjectNotFoundException {
        return projects.values()
                .stream()
                .filter((project) -> project.getId().equalsIgnoreCase(id))
                .findAny()
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    /**
     * Returns an array containing all projects currently managed by the service.
     *
     * @return A copy of the array of all current projects.
     */
    public Map<String, Project> getAllProjects() {
        return projects;
//        return Arrays.copyOf(projects, projectCount);
    }

    /**
     * Returns the current number of projects managed by the service.
     *
     * @return The total count of projects.
     */
    public int getProjectCount() {
        return projects.size();
    }

    /**
     * Displays the detailed information of a single project, including its basic
     * details and a table of its associated tasks.
     *
     * @param project The project to display.
     */
    public void displayProject(Project project) {
        Util.displayText(
                String.format(
                        "Project Name: %s\nType: %s\nTeam Size: %d\nBudget: $%.2f\n",
                        project.getName(),
                        project.getType(),
                        project.getTeamSize(),
                        project.getBudget()));

        Util.displayText("Associated Tasks:");

        if (!project.getTasks().isEmpty()) {
            final int ROW_WIDTH = 52;

            Util.displayTableHeader(
                    ROW_WIDTH,
                    "| %-4s | %-20s | %-20s |",
                    "ID",
                    "TASK NAME",
                    "STATUS");

            project.getTasks().forEach((task) -> Util.displayTableRow(
                    ROW_WIDTH,
                    "| %-4s | %-20s | %-20s |",
                    task.getId(),
                    task.getName(),
                    task.getStatus()));

            System.out.printf("\nCompletion Rate: %.2f%%\n", taskService.calculateCompletionRate(project));
        } else {
            Util.displayText("No task added yet. Added tasks will display here.");
        }
    }

    /**
     * Displays a summary table of all projects currently in the service.
     */
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

        projects.forEach((projectId, project) -> Util.displayTableRow(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                project.getId(),
                project.getName(),
                project.getType(),
                project.getTeamSize(),
                project.getBudget(),
                project.getDescription()));
    }

    /**
     * Finds a project using the project object's 'equals' method and then
     * displays its details.
     *
     * @param project The project object to find and display.
     * @throws ProjectNotFoundException if the project is not found in the list.
     */
    public void displayProjectDetails(Project project) throws ProjectNotFoundException {
        var targetProject = getProjectById(project.getId());

        displayProject(targetProject);
    }

    /**
     * Creates and adds a new {@code SoftwareProject} to the service.
     *
     * @param name        The name of the software project.
     * @param description The description of the software project.
     * @param teamSize    The size of the project team.
     * @param budget      The budget allocated for the software project.
     */
    public void addSoftwareProject(String name,
                                   String description,
                                   int teamSize,
                                   double budget) {
        addProject(new SoftwareProject(name, description, teamSize, budget));
    }

    /**
     * Creates and adds a new {@code HardwareProject} to the service.
     *
     * @param name         The name of the hardware project.
     * @param description  The description of the hardware project.
     * @param teamSize     The size of the project team.
     * @param budget       The budget allocated for the hardware project (excluding material cost).
     * @param materialCost The cost of materials for the hardware project.
     */
    public void addHardwareProject(String name,
                                   String description,
                                   int teamSize,
                                   double budget,
                                   double materialCost) {
        addProject(new HardwareProject(name, description, teamSize, budget, materialCost));
    }
}
