package org.example.interfaces;

import org.example.models.Project;

public interface MainMenu {
    void login();
    void register();
    void manageTask(Project project);
    void manageProject();
    void viewStatusReport();
}
