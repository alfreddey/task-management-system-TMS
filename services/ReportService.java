package services;

import models.Project;
import utils.Util;

public class ReportService {
    private static ReportService service;

    public static ReportService getService() {
        if (service == null) {
            service = new ReportService();
        }

        return service;
    }

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

        Project[] projects = projectService.getAllProjects();
        double sum = 0; // Sum of completion rate;
        var projectCount = projectService.getProjectCount();
        for (int i = 0; i < projectCount; i++) {
            var project = projects[i];

            var taskCount = project.getTaskCount();
            var completionRate = Math.round(project.getCompletionRate());

            var completedTaskCount = Math.round((completionRate / 100) * taskCount);

            sum += completionRate;

            Util.displayTableRow(
                    ROW_WIDTH,
                    "| %-10s | %-15s | %-10s | %-15s | %-15s |",
                    project.getId(),
                    project.getName(),
                    taskCount,
                    completedTaskCount,
                    completionRate);
        }

        var avgCompletionRate = sum / projectCount;
        Util.displayText(
                String.format(
                        "\nAVERAGE COMPLETION: %.2f%%\n",
                        avgCompletionRate));
    }
}
