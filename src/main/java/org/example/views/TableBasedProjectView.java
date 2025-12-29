package org.example.views;

import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.models.Project;
import org.example.utils.Util;

import java.util.function.Predicate;

public class TableBasedProjectView<T extends Project> implements ProjectView<T> {
    private final int ROW_WIDTH;

    public TableBasedProjectView(int rowWidth) {
        this.ROW_WIDTH = rowWidth;
    }

    public TableBasedProjectView() {
        this(132);
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

        projects.forEach(this::viewProject);
    }

    @Override
    public void viewProject(T project) {
        Util.displayTableRow(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                project.getId(),
                project.getName(),
                project.getType(),
                project.getTeamSize(),
                project.getBudget(),
                project.getDescription()
        );
    }

    @Override
    public void viewBy(Predicate<T> condition, ProjectRepository<T> projects) {
        projects.filter(condition).forEach(this::viewProject);
    }
}
