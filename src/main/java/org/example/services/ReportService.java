package org.example.services;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ReportView;
import org.example.models.Project;

public class ReportService{
    private final ReportCalculator reportCalculator;
    private final ReportView reportView;
    private final ProjectRepository<Project> projectRepository;

    public ReportService(ReportCalculator reportCalculator, ReportView reportView, ProjectRepository<Project> projectRepository) {
        this.reportCalculator = reportCalculator;
        this.reportView = reportView;
        this.projectRepository = projectRepository;
    }

    public void viewStatusReport() {
        reportView.viewStatusReport(projectRepository, reportCalculator);
    }

    public ProjectRepository<Project> getProjectRepository() {
        return projectRepository;
    }
}