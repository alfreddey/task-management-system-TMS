package org.example.services;

import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.services.TaskService;
import org.example.interfaces.views.TaskView;
import org.example.models.Project;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.utils.exceptions.TaskNotFoundException;

public class TaskServiceV0<T extends Project> implements TaskService {
    ProjectRepository<T> projectRepository;
    TaskView taskView;

    public TaskServiceV0(ProjectRepository<T> projectRepository, TaskView taskView) {
        this.projectRepository = projectRepository;
        this.taskView = taskView;
    }

    @Override
    public void viewTasks(String projectId) throws ProjectNotFoundException {
        taskView.viewTasks(projectRepository.getById(projectId).getTasks());
    }

    @Override
    public synchronized void updateTaskStatus(String taskId, String projectId, TaskStatus status) throws ProjectNotFoundException, TaskNotFoundException {
        projectRepository
                .getById(projectId)
                .getTasks()
                .updateTaskStatus(
                        (task) -> task.getId().equalsIgnoreCase(taskId),
                        status
                );
    }

    @Override
    public void addNewTask(String projectId, Task task) throws ProjectNotFoundException {
        projectRepository.getById(projectId).getTasks().add(task);
    }

    @Override
    public Task removeTask(String projectId, String taskId) throws ProjectNotFoundException, TaskNotFoundException {
        return projectRepository
                .getById(projectId)
                .getTasks()
                .removeTask(
                        task -> task.getId().equalsIgnoreCase(taskId)
                );
    }
}
