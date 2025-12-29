package org.example.interfaces.views;

import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;

import java.util.function.Predicate;

public interface ProjectView<T extends Project> {
    void viewProjects(UnaryIterable<T> iterableProjects);
    void viewProject(T project);
    void viewBy(Predicate<T> condition, ProjectRepository<T> projectRepository);
}
