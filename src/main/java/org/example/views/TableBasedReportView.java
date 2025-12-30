package org.example.views;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ReportView;
import org.example.models.Project;
import org.example.utils.Util;

public class TableBasedReportView implements ReportView {
    final int ROW_WIDTH;

    public TableBasedReportView(int rowWidth) {
        this.ROW_WIDTH = rowWidth;
    }

    public TableBasedReportView() {
        this(79);
    }

    @Override
    public void viewStatusReport(ProjectRepository<Project> projects, ReportCalculator calculator) {
        if (projects.size() <= 0) {
            Util.displayText("No project available. Add a project to view status report");
        } else {
            Util.displayTableHeader(
                    ROW_WIDTH,
                    "| %-10s | %-15s | %-10s | %-15s | %-15s |",
                    "PROJECT ID",
                    "PROJECT NAME",
                    "TASKS",
                    "COMPLETED",
                    "PROGRESS (%)");
        }
    }
}
