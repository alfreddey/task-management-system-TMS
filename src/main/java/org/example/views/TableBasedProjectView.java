package org.example.views;

import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.interfaces.views.TaskView;
import org.example.models.Project;
import org.example.utils.Util;

import java.util.function.Predicate;

public class TableBasedProjectView<T extends Project> implements ProjectView<T> {
    private final int ROW_WIDTH;
    private final TaskView taskView;

    public TableBasedProjectView(int rowWidth, TaskView taskView) {
        this.ROW_WIDTH = rowWidth;
        this.taskView = taskView;
    }

    public TableBasedProjectView() {
        this(132, new TableBasedTaskView());
    }

    @Override
    public void viewProjects(UnaryIterable<T> projects) {
        Util.displayTableHeader(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                "ID",
                "PROJECT NAME",
                "TYPE",
                "TEAM SIZE",
                "BUDGET",
                "DESCRIPTION"
        );

        projects.forEach(project -> Util.displayTableRow(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                project.getId(),
                project.getName(),
                project.getType(),
                project.getTeamSize(),
                project.getBudget(),
                project.getDescription()
        ));
    }

    public void viewProject(T project) {
        System.out.printf("Project Name: %s", project.getName());
        System.out.printf("\nType: %s", project.getType());
        System.out.printf("\nTeam size: %s", project.getTeamSize());
        System.out.printf("\nBudget: %s\n", project.getBudget());

        var tasks = project.getTasks();

        taskView.viewTasks(tasks);
    }

    @Override
    public void viewBy(Predicate<T> condition, ProjectRepository<T> projects) {
        viewProjects(projects.filter(condition));
    }
}
