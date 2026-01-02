package org.example.repositories;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;
import org.example.utils.exceptions.ProjectNotFoundException;

import java.util.function.Predicate;

public class ArrayBasedProjectRepository<T extends Project> implements ProjectRepository<T> {
    private static final int MAX_SIZE = 998;
    private final T[] projects;
    private int count = 0;

    @SuppressWarnings("unchecked")
    public ArrayBasedProjectRepository() {
        this.projects = (T[]) new Project[MAX_SIZE];
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void add(T project) {
        if (count >= MAX_SIZE) {
            throw new IllegalStateException("Repository is full");
        }
        projects[count++] = project;
    }

    @Override
    public T get(Predicate<T> condition) throws ProjectNotFoundException {
        for (int i = 0; i < count; i++) {
            if (condition.test(projects[i])) {
                return projects[i];
            }
        }
        throw new ProjectNotFoundException("No project matching condition found");
    }

    @Override
    public T getById(String id) throws ProjectNotFoundException {
        return get(p -> p.getId().equalsIgnoreCase(id));
    }

    @Override
    public ProjectRepository<T> filter(Predicate<T> condition) {
        ArrayBasedProjectRepository<T> filtered = new ArrayBasedProjectRepository<>();
        for (int i = 0; i < count; i++) {
            if (condition.test(projects[i])) {
                filtered.add(projects[i]);
            }
        }
        return filtered;
    }

    @Override
    public T remove(Predicate<T> condition) throws ProjectNotFoundException {
        for (int i = 0; i < count; i++) {
            if (condition.test(projects[i])) {
                T removed = projects[i];
                // Shift elements left
                for (int j = i; j < count - 1; j++) {
                    projects[j] = projects[j + 1];
                }
                projects[--count] = null;
                return removed;
            }
        }
        throw new ProjectNotFoundException("No project matching condition found to remove");
    }

    @Override
    public void forEach(java.util.function.Consumer<T> action) {
        for (int i = 0; i < count; i++) {
            action.accept(projects[i]);
        }
    }

}
