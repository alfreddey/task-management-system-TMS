package org.example.services;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.models.*;
import org.example.utils.exceptions.ProjectNotFoundException;

/**
 * Handles project application logic
 * @param <T>
 */
public class ProjectService<T extends Project> {
    private final ProjectRepository<T> projectRepository;
    private final ProjectView<T> projectView;
    private final ReportCalculator reportCalculator;

    public ProjectService(ProjectRepository<T> projectRepository, ProjectView<T> projectView, ReportCalculator reportCalculator) {
        this.projectRepository = projectRepository;
        this.projectView = projectView;
        this.reportCalculator = reportCalculator;
    }

    public void addNewProject(T project) {
        projectRepository.add(project);
    }

    public void viewProjects() {
        projectView.viewProjects(projectRepository);
    }

    public void viewProject(String projectId) throws ProjectNotFoundException {
        var project = projectRepository.getById(projectId);

        projectView.viewProject(project);

        System.out.printf("\nCompletion Rate: %.2f%%\n", reportCalculator.calculateTaskProgressionRate(project.getTasks()));
    }

    public void viewByType(ProjectType type) {
        projectView.viewBy(project -> project.getType().equals(type), projectRepository);
    }

    public void viewByBudgetRange(double min, double max) {
        projectView.viewBy(
                project -> project.getBudget() >= min && project.getBudget() <= max,
                projectRepository
        );
    }

    public ProjectRepository<T> getProjectRepository() { return projectRepository; }
}
