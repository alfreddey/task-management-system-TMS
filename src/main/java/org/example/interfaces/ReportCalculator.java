package org.example.interfaces;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.repositories.TaskRepository;

public interface ReportCalculator {
    double calculateAverageCompletionRate(ProjectRepository projects);
    int calculateCompletedTasks(TaskRepository tasks);
    double calculateTaskProgressionRate(TaskRepository tasks);
}
