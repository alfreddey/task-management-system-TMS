package org.example.interfaces.repositories;

import org.example.interfaces.UnaryIterable;
import org.example.models.Project;
import org.example.utils.exceptions.ProjectNotFoundException;

import java.util.function.Predicate;

public interface ProjectRepository<T extends Project> extends UnaryIterable<T> {
    int size();
    void add(T project);
    T get(Predicate<T> condition) throws ProjectNotFoundException;
    T getById(String id) throws ProjectNotFoundException;
    ProjectRepository<T> filter(Predicate<T> condition);
    T remove(Predicate<T> condition) throws ProjectNotFoundException;
}
