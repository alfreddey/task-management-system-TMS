package services;

import models.Project;

public class ReportService {
    private static ReportService service;

    public static ReportService getService() {
        if (service == null) {
            service = new ReportService();
        }

        return service;
    }

    public void generateReport(ProjectService projectService) {
        System.out.println("-".repeat(81));
        System.out.printf("| %-10s | %-15s | %-10s | %-15s | %-15s |\n",
                "PROJECT ID",
                "PROJECT NAME",
                "TASKS",
                "COMPLETED",
                "PROGRESS (%)");
        System.out.println("-".repeat(81));

        Project[] projects = projectService.getAllProjects();
        double sum = 0; // Sum of completion rate;
        var projectCount = projectService.getProjectCount();
        for (int i = 0; i < projectCount; i++) {
            var project = projects[i];

            var taskCount = project.getTaskCount();
            var completionRate = project.getCompletionRate();

            var completedTaskCount = Math.round((completionRate / 100) * taskCount);

            sum += completionRate;

            System.out.printf("| %-10s | %-15s | %-10d | %-15d | %-15.2f |\n",
                    project.getId(),
                    project.getName(),
                    taskCount,
                    completedTaskCount,
                    completionRate);
            System.out.println("-".repeat(81));
        }

        var avgCompletionRate = sum / projectCount;
        System.out.printf("\nAVERAGE COMPLETION: %.2f%%\n", avgCompletionRate);
    }
}
