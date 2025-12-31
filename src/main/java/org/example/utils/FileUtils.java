package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.models.Project;
import org.example.repositories.MapBasedProjectRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final Path FILE_PATH = Path.of("data/projects_data.json");
    private static final Gson gson = new Gson();

    public static <T extends Project> void saveProjects(ProjectRepository<T> repository) {
        List<T> projectList = new ArrayList<>();
        repository.forEach(projectList::add);

        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, gson.toJson(projectList));
            System.out.println("Projects saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save projects: " + e.getMessage());
        }
    }

    public static <T extends Project> ProjectRepository<T> loadProjects(Class<T> clazz) {
        if (!Files.exists(FILE_PATH)) {
            System.out.println("No projects file found, returning empty repository.");
            return new MapBasedProjectRepository<>();
        }

        try {
            String json = Files.readString(FILE_PATH);
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> projectList = gson.fromJson(json, listType);
            MapBasedProjectRepository<T> repository = new MapBasedProjectRepository<>();
            projectList.forEach(repository::add);
            System.out.println("Projects loaded successfully.");
            return repository;
        } catch (IOException e) {
            System.err.println("Failed to load projects: " + e.getMessage());
            return new MapBasedProjectRepository<>();
        }
    }
}
