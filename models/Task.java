package models;

import interfaces.Completable;

import java.util.Date;

public class Task implements Completable {
    private static int index = 0;
    private TaskStatus status;
    private String id;
    private String name;
    private String projectId;
    private Date hour;

    // Constructors
    public Task(String name, TaskStatus status, String projectId) {
        index += 1;
        this.id = String.format("T%03d", index);
        this.name = name;
        this.status = status;
        this.projectId = projectId;
    }

    // Getters
    public String getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    };

    public String getName() {
        return name;
    }

    // Setters
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    // Interface methods
    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETED;
    };
}
