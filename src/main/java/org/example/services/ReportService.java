package org.example.services;

import org.example.utils.Util;

/**
 * Provides functionality for generating various reports based on project data.
 * This class follows the Singleton pattern.
 */
public class ReportService {
    private static ReportService service;
    private static TaskService taskService;

    /**
     * Returns the singleton instance of the ReportService.
     * If the instance does not exist, it creates one.
     *
     * @return The single instance of ReportService.
     */
    public static ReportService getService() {
        if (service == null) {
            service = new ReportService();
            taskService = TaskService.getService();
        }

        return service;
    }

    /**
     * Generates a comprehensive project progress report displaying key metrics
     * for all managed projects, including task counts, completed tasks, and
     * completion percentage. It also calculates and displays the average
     * completion rate across all projects.
     *
     * @param projectService The {@code ProjectService} instance used to retrieve
     *                       the list of projects.
     */
    public void generateReport(ProjectService projectService) {
        final int ROW_WIDTH = 79;

        Util.displayTableHeader(
                ROW_WIDTH,
                "| %-10s | %-15s | %-10s | %-15s | %-15s |",
                "PROJECT ID",
                "PROJECT NAME",
                "TASKS",
                "COMPLETED",
                "PROGRESS (%)");

        var projects = projectService.getAllProjects();
        final double[] sum = {0};
        projects.forEach((projectId,project) -> {
            var taskCount = project.getTaskCount();
            double completionRate = Math.round(taskService.calculateCompletionRate(project));

            var completedTaskCount = Math.round(((float) completionRate / 100) * taskCount);

            sum[0] += completionRate;

            Util.displayTableRow(
                    ROW_WIDTH,
                    "| %-10s | %-15s | %-10s | %-15s | %-15s |",
                    project.getId(),
                    project.getName(),
                    taskCount,
                    completedTaskCount,
                    completionRate);
        });

        var avgCompletionRate = sum[0] / projects.size();
        Util.displayText(
                String.format(
                        "\nAVERAGE COMPLETION: %.2f%%\n",
                        avgCompletionRate));
    }
}
