package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import models.Project;
import models.ProjectType;
import models.User;
import services.ProjectService;
import services.UserService;

public class ConsoleMenu {
    private UserService userService;
    private ProjectService projectService;
    private Scanner scanner;
    private User user;

    public ConsoleMenu(Scanner scanner, UserService userService, ProjectService projectService) {
        this.scanner = scanner;
        this.userService = userService;
        this.projectService = projectService;
        this.user = null;
    }

    public ConsoleMenu(Scanner scanner) {
        this(scanner, UserService.getService(), ProjectService.getService());
    }

    public void start() throws Exception {
        // Populate the user and project list
        Util.seedDatabase(projectService, userService);

        boolean running = true;
        do {
            Util.displayAsHeading("JAVA TASK MANAGEMENT SYSTEM");

            Util.displayText("Welcome, Enter S to sign in, R to register or Q to quit the program");

            Util.displayAsPrompt("Enter your choice");

            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "S":
                    signInUser();
                    break;
                case "R":
                    registerUser();
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    Util.displayAsError("Invalid input. Please try again.");
            }
        } while (running);
    }

    public void signInUser() {
        boolean signedIn = false;

        do {
            try {
                Util.displayAsHeading("SIGN IN PORTAL");

                Util.displayText("Welcome, please enter your email below or Q to return");

                Util.displayAsPrompt("Enter your email here");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q")) {
                    return;
                }

                user = userService.getUserByEmail(input);

                Util.displayText(
                        String.format(
                                "\nYou are successfully signed in as %s",
                                Util.capitalizeText(user.getName())));

                switch (user.getRole()) {
                    case ADMIN:
                        // Go to main menu IF user is ADMIN
                        mainMenu();
                        break;
                    case REGULAR:
                        // Go to project catalog menu, otherwise
                        projectCatalog();
                        break;
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!signedIn);
    };

    public void registerUser() {
    };

    public void mainMenu() {
        ArrayList<String> menuList = new ArrayList<>();

        menuList.add("Manage Projects");
        menuList.add("Manage Tasks");
        menuList.add("View Status Reports");
        menuList.add("Switch User");
        menuList.add("Exit");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("Java Task Management System");

                Util.displayText(
                        String.format("Current User: %s (%s)",
                                Util.capitalizeText(
                                        user.getName()),
                                user.getRole()));

                Util.displayAsMenu("Main Menu", menuList);

                Util.displayAsPrompt("\nEnter your choice");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        manageProjectMenu();
                        break;
                    case "2":
                        manageTaskMenu();
                        break;
                    case "3":
                        viewStatusReports();
                        break;
                    case "4":
                        switchUser();
                        break;
                    case "5":
                        running = false;
                        break;
                    default:
                        throw new Exception("Please select a valid option(1, 2, ..)");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    };

    private void switchUser() {
    }

    private void viewStatusReports() {
    }

    private void manageTaskMenu() {
    }

    public void projectCatalog() {
        ArrayList<String> menuList = new ArrayList<>();

        menuList.add(String.format(
                "View All Projects (%s)",
                projectService.getProjectCount()));
        menuList.add("Software Projects only");
        menuList.add("Hardware Projects only");
        menuList.add("Search by Budget Range");
        menuList.add("Go back");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("Project Catalog");

                Util.displayAsMenu("Filter Options", menuList);

                Util.displayAsPrompt("\nEnter filter choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        viewAllProjects();
                        break;
                    case "2":
                        viewProjectByType(ProjectType.SOFTWARE);
                        break;
                    case "3":
                        viewProjectByType(ProjectType.HARDWARE);
                        break;
                    case "4":
                        searchByBudgetRange();
                        break;
                    case "5":
                        running = false;
                        break;
                    default:
                        throw new Exception("Invalid filter choice. Please choose between 1 and 5");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    };

    private void searchByBudgetRange() {
    };

    private void viewProjectByType(ProjectType type) {
        Project[] projects = projectService.getAllProjects();

        final int ROW_WIDTH = 132;
        Util.displayTableHeader(
                ROW_WIDTH,
                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                "ID",
                "PROJECT NAME",
                "TYPE",
                "TEAM SIZE",
                "BUDGET",
                "DESCRIPTION");

        Arrays
                .stream(projects)
                .filter(project -> project.getType() == type)
                .forEach(project -> {
                    Util.displayTableRow(
                            ROW_WIDTH,
                            "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                            project.getId(),
                            project.getName(),
                            project.getType(),
                            project.getTeamSize(),
                            project.getBudget(),
                            project.getDescription());
                });
    };

    public void viewAllProjects() {
        projectService.displayAllProjects();

        boolean running = true;
        Project project = null;
        do {
            try {
                Util.displayAsPrompt("\nEnter project ID to view details (or 0 to return)");

                String input = scanner.nextLine();

                if (input.equals("0")) {
                    return;
                }

                // Throws an exception IF projectID is invalid
                ValidationUtils.validateProjectID(input);

                project = projectService.getProjectById(input);
                running = false;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);

        // Go to Project Details menu
        projectDetailsOf(project);
    };

    public void projectDetailsOf(Project project) {

    }

    public void manageProjectMenu() {
    };
}