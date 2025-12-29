package org.example.views;

import org.example.interfaces.UnaryIterable;
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
    public void viewTasks(UnaryIterable<Task> tasks) {
        Util.displayTableHeader(
                ROW_WIDTH,
                "| %-4s | %-20s | %-20s |",
                "ID",
                "TASK NAME",
                "STATUS");

        tasks.forEach(this::viewTask);
    }

    @Override
    public void viewTask(Task task) {
        Util.displayTableRow(
                ROW_WIDTH,
                "| %-4s | %-20s | %-20s |",
                task.getId(),
                task.getName(),
                task.getStatus());
    }
}
