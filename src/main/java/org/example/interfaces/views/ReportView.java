package org.example.interfaces.views;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;

public interface ReportView {
    <T extends Project> void viewStatusReport(ProjectRepository<T> projects, ReportCalculator calculator);
}
