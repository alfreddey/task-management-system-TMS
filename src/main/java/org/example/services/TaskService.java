package org.example.services;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.repositories.TaskRepository;
import org.example.models.Project;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.repositories.ListBasedTaskRepository;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.utils.exceptions.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TaskService<T extends Project> {
    ProjectRepository<T> projectRepository;

    public TaskService(ProjectRepository<T> projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Task getTask(String projectId, String taskId) throws ProjectNotFoundException, TaskNotFoundException {
        return projectRepository.getById(projectId).getTasks().get(task -> task.getId().equalsIgnoreCase(taskId));
    }

    public Task getTaskByName(String projectId, String taskName) throws ProjectNotFoundException, TaskNotFoundException {
        return projectRepository.getById(projectId).getTasks().get(task -> task.getName().equalsIgnoreCase(taskName));
    }

    public Task updateTaskStatus(String taskId, String projectId, TaskStatus status) throws ProjectNotFoundException, TaskNotFoundException {
        var targetProject = projectRepository.getById(projectId);

        return targetProject.getTasks().updateTaskStatus((task) -> task.getId().equalsIgnoreCase(taskId), status);
    }

    public void addNewTask(String projectId, Task task) throws ProjectNotFoundException  {
        projectRepository.getById(projectId).getTasks().add(task);
    }

    public Task removeTask(String projectId, String taskId) throws ProjectNotFoundException, TaskNotFoundException {
        return projectRepository.getById(projectId).getTasks().removeTask(task -> task.getId().equalsIgnoreCase(taskId));
    }
}
