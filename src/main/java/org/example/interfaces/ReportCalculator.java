package org.example.interfaces;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.repositories.TaskRepository;
import org.example.models.Project;

public interface ReportCalculator {
    double calculateAverageCompletionRate(ProjectRepository<Project> projects);
    int calculateCompletedTasks(TaskRepository tasks);
    double calculateTaskProgressionRate(TaskRepository tasks);
}
