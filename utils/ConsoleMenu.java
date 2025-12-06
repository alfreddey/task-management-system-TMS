package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import models.AdminUser;
import models.Project;
import models.ProjectType;
import models.RegularUser;
import models.Task;
import models.TaskStatus;
import models.User;
import models.UserRole;
import services.ProjectService;
import services.ReportService;
import services.UserService;

public class ConsoleMenu {
    private UserService userService;
    private ProjectService projectService;
    private ReportService reportService;
    private Scanner scanner;
    private User user;

    public ConsoleMenu(Scanner scanner, UserService userService, ProjectService projectService,
            ReportService reportService) {
        this.scanner = scanner;
        this.userService = userService;
        this.projectService = projectService;
        this.reportService = reportService;
        this.user = null;
    }

    public ConsoleMenu(Scanner scanner) {
        this(scanner, UserService.getService(), ProjectService.getService(), ReportService.getService());
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

    private void signInUser() {
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

    private void registerUser() {
        String input;
        String name;
        String email;

        Util.displayAsHeading("Registration portal");

        Util.displayAsPrompt("Welcome, enter your name here");
        name = scanner.nextLine();

        Util.displayAsPrompt("\nEnter your email");
        email = scanner.nextLine();

        boolean valid = true;
        do {
            try {
                Util.displayAsPrompt("\nEnter your role (Admin - A / Regular - R)");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("A")) {
                    user = new AdminUser(name, email);
                    userService.addUser(user);

                    Util.displayText(String.format("\nUser %s added successfully\n", name));

                    mainMenu();
                    valid = true;
                } else if (input.equalsIgnoreCase("R")) {
                    user = new RegularUser(name, email);
                    userService.addUser(user);

                    Util.displayText(String.format("\nUser %s added successfully\n", name));

                    projectCatalog();

                    valid = true;
                } else {
                    valid = false;
                    throw new Exception("Incorrect role. Please enter A for admin, and R for regular");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);
    };

    private void mainMenu() {
        boolean running = true;
        do {
            try {
                if (user.getRole() != UserRole.ADMIN) {
                    Util.displayAsError("Access denied. Only Admins can access the main menu");
                    projectCatalog();
                    return;
                }

                ArrayList<String> menuItems = new ArrayList<>();

                menuItems.add("Manage Projects");
                menuItems.add("Manage Tasks");
                menuItems.add("View Status Reports");
                menuItems.add("Switch User");
                menuItems.add("Exit");

                Util.displayAsHeading("Java Task Management System");

                Util.displayText(
                        String.format("Current User: %s (%s)",
                                Util.capitalizeText(
                                        user.getName()),
                                user.getRole()));

                Util.displayAsMenu("Main Menu", menuItems);

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
        try {
            Util.displayAsHeading("SWITCH USER");

            Util.displayAsPrompt("Enter the email to switch user");
            String email = scanner.nextLine();

            user = userService.getUserByEmail(email);

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
    }

    private void viewStatusReports() {
        Util.displayAsHeading("PROJECT STATUS REPORT");

        reportService.generateReport(projectService);
    }

    private void manageTaskMenu() {
    }

    private void projectCatalog() {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add(String.format(
                "View All Projects (%s)",
                projectService.getProjectCount()));
        menuItems.add("Software Projects only");
        menuItems.add("Hardware Projects only");
        menuItems.add("Search by Budget Range");
        menuItems.add("Go back");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("Project Catalog");

                Util.displayAsMenu("Filter Options", menuItems);

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
        Util.displayAsHeading("SEARCH BY BUDGET RANGE");

        double minimumBudgetRange = -1;
        do {
            try {
                Util.displayAsPrompt("Please enter minimum budget range ( > 0 )");
                String input = scanner.nextLine();

                minimumBudgetRange = Double.parseDouble(input);

            } catch (NumberFormatException e) {
                Util.displayAsError(e.getMessage());
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (minimumBudgetRange <= 0);

        double maximumBudgetRange = -1;
        do {
            try {
                Util.displayAsPrompt("Please enter maximum budget range ( > 0 )");
                String input = scanner.nextLine();

                maximumBudgetRange = Double.parseDouble(input);

            } catch (NumberFormatException e) {
                Util.displayAsError(e.getMessage());
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (maximumBudgetRange <= 0);

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

        for (Project project : projects) {
            if (project.getBudget() >= minimumBudgetRange
                    && project.getBudget() <= maximumBudgetRange) {
                Util.displayTableRow(
                        ROW_WIDTH,
                        "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
                        project.getId(),
                        project.getName(),
                        project.getType(),
                        project.getTeamSize(),
                        project.getBudget(),
                        project.getDescription());
            }
        }
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

    private void viewAllProjects() {
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

                if (user.getRole() == UserRole.ADMIN) {
                    projectDetailsMenuForAdminUser(project);
                } else {
                    projectDetailsMenuForRegularUser(project);
                }

            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    };

    private void projectDetailsMenuForRegularUser(Project project) {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add("Add new task");
        menuItems.add("Update task status");
        menuItems.add("Back to Main Menu");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading(String.format("Project Details: %s", project.getId()));

                projectService.displayProjectDetails(project);

                Util.displayAsMenu("Options", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask(project);
                        break;
                    case "2":
                        updateTaskStatus(project);
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        throw new Exception("Invalid input, please choose between 1 and 4");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    }

    private void projectDetailsMenuForAdminUser(Project project) {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add("Add new task");
        menuItems.add("Update task status");
        menuItems.add("Remove task");
        menuItems.add("Back to Main Menu");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading(String.format("Project Details: %s", project.getId()));

                projectService.displayProjectDetails(project);

                Util.displayAsMenu("Options", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask(project);
                        break;
                    case "2":
                        updateTaskStatus(project);
                        break;
                    case "3":
                        removeTaskById();
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        throw new Exception("Invalid input, please choose between 1 and 4");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    }

    private void removeTaskById() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTaskById'");
    }

    private void addNewTask(Project project) {
        Util.displayAsHeading("ADD NEW TASK");

        Util.displayAsPrompt("Enter task name");
        String taskName = scanner.nextLine();

        // Read and validate ProjectID
        boolean valid = false;
        String projectId;
        do {
            Util.displayAsPrompt("\nEnter assigned project ID");
            projectId = scanner.nextLine();

            try {
                ValidationUtils.validateProjectID(projectId);

                project = projectService.getProjectById(projectId);

                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);

        // Read and validate Task Status
        boolean done = false;
        TaskStatus taskStatus = null;
        do {
            Util.displayAsPrompt("\nEnter initial status (Pending (P) /In Progress (I) /Completed (C))");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "P":
                    taskStatus = TaskStatus.PENDING;
                    done = true;
                    break;
                case "I":
                    taskStatus = TaskStatus.IN_PROGRESS;
                    done = true;
                    break;
                case "C":
                    taskStatus = TaskStatus.COMPLETED;
                    done = true;
                    break;
                default:
                    Util.displayAsError(
                            "Error: Invalid input. Please enter a valid status (Pending/In Progress/Completed)");
            }
        } while (!done);

        project.addTask(new Task(taskName, taskStatus, projectId));

        Util.displayText(String.format(
                "\nTask '%s' added successfully to Project %s\n",
                taskName,
                project.getId()));
    }

    private void updateTaskStatus(Project project) {
        Util.displayAsHeading("UPDATE TASK STATUS");

        boolean valid = false;
        Task task = null;
        do {
            try {
                Util.displayAsPrompt("\nEnter task ID (or Q to quit)");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q"))
                    return;

                String taskId = input;
                ValidationUtils.validateTaskID(taskId);

                task = project.getTaskById(taskId);
                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage() + ". " + "Please try again");
            }
        } while (!valid);

        valid = false;
        String taskStatus;
        do {
            Util.displayAsPrompt("\nEnter new status (Pending (P) / In Progress (I) / Completed (C))");
            taskStatus = scanner.nextLine().toUpperCase();

            switch (taskStatus) {
                case "P":
                    task.setStatus(TaskStatus.PENDING);
                    valid = true;
                    break;
                case "I":
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    valid = true;
                    break;
                case "C":
                    task.setStatus(TaskStatus.COMPLETED);
                    valid = true;
                    break;
                default:
                    Util.displayAsError("Error: Invalid Status. Please choose from Pending / In progress / Completed");
            }
        } while (!valid);

        Util.displayText(
                String.format(
                        "\nTask '%s' marked as %s\n",
                        task.getName(),
                        task.getStatus()));
    }

    private void manageProjectMenu() {
    };
}