package org.example.models;

import org.example.interfaces.Completable;

public class Task implements Completable {
    private static int index = 0;
    private TaskStatus status;
    private final String id;
    private final String name;
    private final String projectId;

    public Task(String name, TaskStatus status, String projectId) {
        index += 1;
        this.id = String.format("T%03d", index);
        this.name = name;
        this.status = status;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETED;
    }
}
