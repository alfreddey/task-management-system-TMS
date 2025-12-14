package org.example;

import java.util.Scanner;

import org.example.services.ProjectService;
import org.example.services.ReportService;
import org.example.services.TaskService;
import org.example.services.UserService;
import org.example.utils.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = UserService.getService();
        ProjectService projectService = ProjectService.getService();
        ReportService reportService = ReportService.getService();
        TaskService taskService = TaskService.getService();

        try {
            ConsoleMenu consoleMenu = new ConsoleMenu(scanner, userService, projectService, reportService, taskService);
            consoleMenu.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}