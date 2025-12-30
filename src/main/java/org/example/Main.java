package org.example;

import java.util.Scanner;

import org.example.interfaces.Menu;
import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.interfaces.views.ReportView;
import org.example.interfaces.views.TaskView;
import org.example.models.Project;
import org.example.repositories.ListBasedTaskRepository;
import org.example.repositories.MapBasedProjectRepository;
import org.example.services.ProjectService;
import org.example.services.ReportService;
import org.example.services.TaskService;
import org.example.services.UserService;
import org.example.utils.ConsoleMenu;
import org.example.utils.DefaultReportCalculator;
import org.example.utils.MenuV0;
import org.example.views.TableBasedProjectView;
import org.example.views.TableBasedReportView;
import org.example.views.TableBasedTaskView;

public class Main {
    public static void main(String[] args) {

      try (Scanner scanner = new Scanner(System.in)) {
        ProjectRepository<Project> projectRepository = new MapBasedProjectRepository<>();

        ProjectView<Project> projectView = new TableBasedProjectView<>();
        ReportView reportView = new TableBasedReportView();
        TaskView taskView = new TableBasedTaskView();

        ReportCalculator reportCalculator = new DefaultReportCalculator();

        UserService userService = UserService.getService();

        ProjectService<Project> projectService = new ProjectService<>(
                projectRepository,
                projectView,
                reportCalculator
        );

        ReportService reportService = new ReportService(
                new DefaultReportCalculator(),
                reportView,
                projectRepository
        );

        TaskService<Project> taskService = new TaskService<>(projectRepository, taskView);

        Menu consoleMenu = new MenuV0(scanner, projectService, taskService, userService, reportService);
        consoleMenu.start();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
}