package org.example.repositories;

import org.example.Main;
import org.example.interfaces.UnaryIterable;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;
import org.example.utils.exceptions.ProjectNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapBasedProjectRepository<T extends Project> implements ProjectRepository<T>, UnaryIterable<T> {
    Map<String, T> projects;

    public MapBasedProjectRepository(Map<String, T> projects) {
        this.projects = projects;
    }

    public MapBasedProjectRepository() {
        this.projects = new HashMap<>();
    }

    @Override
    public int size() {
        return projects.size();
    }

    @Override
    public void add(T project) {
        projects.put(project.getId().toUpperCase(), project);
    }

    @Override
    public T get(Predicate<T> condition) throws ProjectNotFoundException {
        return projects.values()
                .stream()
                .filter(condition)
                .findFirst()
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    @Override
    public T getById(String id) {
        return projects.get(id.toUpperCase());
    }

    @Override
    public ProjectRepository<T> filter(Predicate<T> condition) {
        var filteredProjects = projects.entrySet()
                .stream()
                .filter((entry) -> condition.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new MapBasedProjectRepository<>(new HashMap<>(filteredProjects));
    }

    @Override
    public T remove(Predicate<T> condition) throws ProjectNotFoundException {
        return null;
    }

    @Override
    public void forEach(Consumer<T> action) {
        projects.values().forEach(action);
    }
}
