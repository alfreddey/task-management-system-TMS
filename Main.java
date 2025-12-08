import java.util.Scanner;

import services.ProjectService;
import services.ReportService;
import services.UserService;
import utils.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = UserService.getService();
        ProjectService projectService = ProjectService.getService();
        ReportService reportService = ReportService.getService();

        try {
            ConsoleMenu consoleMenu = new ConsoleMenu(scanner, userService, projectService, reportService);
            consoleMenu.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}