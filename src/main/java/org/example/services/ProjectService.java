package org.example.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.interfaces.views.TaskView;
import org.example.models.*;
import org.example.repositories.ListBasedTaskRepository;
import org.example.repositories.MapBasedProjectRepository;
import org.example.utils.Util;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.views.TableBasedProjectView;
import org.example.views.TableBasedTaskView;


public class ProjectService<T extends Project> {
    ProjectRepository<T> projectRepository;

    public ProjectService(ProjectRepository<T> projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void addNewProject(T project) {
        projectRepository.add(project);
    }
}
