package org.example.views;

import org.example.interfaces.repositories.TaskRepository;
import org.example.interfaces.views.TaskView;
import org.example.models.Task;
import org.example.utils.Util;

public class TableBasedTaskView implements TaskView {
    private final int ROW_WIDTH;

    public TableBasedTaskView(int rowWidth) {
        this.ROW_WIDTH = rowWidth;
    }

    public TableBasedTaskView() {
        this(52);
    }

    @Override
    public void viewTasks(TaskRepository tasks) {
        if (tasks.size() <= 0) {
            System.out.println("\nNo task associated with this project yet.");
        } else {
            Util.displayTableHeader(
                    ROW_WIDTH,
                    "| %-4s | %-20s | %-20s |",
                    "ID",
                    "TASK NAME",
                    "STATUS");

            tasks.forEach(task -> Util.displayTableRow(
                    ROW_WIDTH,
                    "| %-4s | %-20s | %-20s |",
                    task.getId(),
                    task.getName(),
                    task.getStatus()));
        }
    }

    @Override
    public void viewTask(Task task) {
        System.out.printf("\nTask Name: %s", task.getName());
        System.out.printf("\nStatus: %s", task.getStatus());
    }
}
