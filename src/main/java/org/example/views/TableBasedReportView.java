package org.example.views;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ReportView;
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
    public void viewStatusReport(ProjectRepository projects, ReportCalculator calculator) {
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
