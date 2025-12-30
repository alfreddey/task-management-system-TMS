package org.example.interfaces.views;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;

public interface ReportView {
    void viewStatusReport(ProjectRepository<Project> projects, ReportCalculator calculator);
}
