package org.example.utils;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.repositories.TaskRepository;
import org.example.models.Project;
import org.example.models.Task;

public class DefaultReportCalculator implements ReportCalculator {
    @Override
    public double calculateAverageCompletionRate(ProjectRepository<Project> projects) {
        return 0;
    }

    @Override
    public int calculateCompletedTasks(TaskRepository tasks) {
        return tasks.filter(Task::isCompleted).size();
    }

    @Override
    public double calculateTaskProgressionRate(TaskRepository tasks) {
        return tasks.size() <= 0 ? 0 : (double) calculateCompletedTasks(tasks) / tasks.size() * 100;
    }
}
