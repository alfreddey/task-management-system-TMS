package org.example.interfaces;

import org.example.models.Project;

public interface Menu {
    void start();
    void login();
    void register();
    void manageTask(Project project);
    void manageProject();
    void viewStatusReport();
}
