package org.example.menus;

import java.util.ArrayList;
import java.util.Scanner;

import org.example.models.HardwareProject;
import org.example.models.Project;
import org.example.models.ProjectType;
import org.example.models.SoftwareProject;
import org.example.models.Task;
import org.example.models.TaskStatus;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.repositories.ListBasedTaskRepository;
import org.example.services.ProjectService;
import org.example.services.ReportService;
import org.example.services.TaskServiceV0;
import org.example.services.UserService;
import org.example.utils.Util;
import org.example.utils.ValidationUtils;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.example.utils.exceptions.TaskNotFoundException;

public class ConsoleMenu {
    private final UserService userService;
    private final ProjectService<Project> projectService;
    private final ReportService reportService;
    private final TaskServiceV0<Project> taskService;
    private final Scanner scanner;
    private User user;

    public ConsoleMenu(
            Scanner scanner,
            UserService userService,
            ProjectService<Project> projectService,
            ReportService reportService,
            TaskServiceV0<Project> taskService
    ) {
        this.scanner = scanner;
        this.userService = userService;
        this.projectService = projectService;
        this.reportService = reportService;
        this.taskService = taskService;
        this.user = null;
    }

    public void start() throws Exception {
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
                        mainMenu();
                        break;
                    case REGULAR:
                        projectCatalog();
                        break;
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (true);
    }

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
                    user = userService.addAdminUser(name, email);

                    Util.displayText(String.format("\nUser %s added successfully\n", name));

                    mainMenu();
                    valid = true;
                } else if (input.equalsIgnoreCase("R")) {
                    user = userService.addRegularUser(name, email);

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
    }

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

                Util.displayAsOption("Main Menu", menuItems);

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
    }

    private void switchUser() {
        try {
            Util.displayAsHeading("SWITCH USER");

            Util.displayAsPrompt("Enter the email to switch user");
            String email = scanner.nextLine();

            user = userService.getUserByEmail(email);

            switch (user.getRole()) {
                case ADMIN:
                    mainMenu();
                    break;
                case REGULAR:
                    projectCatalog();
                    break;
            }
        } catch (Exception e) {
            Util.displayAsError(e.getMessage());
        }
    }

    private void viewStatusReports() {
        Util.displayAsHeading("PROJECT STATUS REPORT");

        reportService.viewStatusReport();
    }

    private void manageTaskMenu() {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add("Add new task");
        menuItems.add("Update task status");
        menuItems.add("Remove task");
        menuItems.add("Back to Main Menu");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("Manage Tasks");

                Util.displayText("Welcome to the Manage Tasks portal");

                Util.displayAsOption("Task Menu", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask();
                        break;
                    case "2":
                        updateTaskStatus();
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

    private void projectCatalog() {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add(String.format(
                "View All Projects (%s)",
                projectService.getProjectRepository().size()));
        menuItems.add("Software Projects only");
        menuItems.add("Hardware Projects only");
        menuItems.add("Search by Budget Range");
        menuItems.add("Go back");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("Project Catalog");

                Util.displayAsOption("Filter Options", menuItems);

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
    }

    private void searchByBudgetRange() {
        Util.displayAsHeading("SEARCH BY BUDGET RANGE");

        double minimumBudgetRange = -1;
        do {
            try {
                Util.displayAsPrompt("Please enter minimum budget range ( > 0 )");
                String input = scanner.nextLine();

                minimumBudgetRange = Double.parseDouble(input);
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
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (maximumBudgetRange <= 0);

//        Project[] projects = projectService.getAllProjects();
//
//        final int ROW_WIDTH = 132;
//        Util.displayTableHeader(
//                ROW_WIDTH,
//                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
//                "ID",
//                "PROJECT NAME",
//                "TYPE",
//                "TEAM SIZE",
//                "BUDGET",
//                "DESCRIPTION");
//
//        for (Project project : projects) {
//            if (project.getBudget() >= minimumBudgetRange
//                    && project.getBudget() <= maximumBudgetRange) {
//                Util.displayTableRow(
//                        ROW_WIDTH,
//                        "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
//                        project.getId(),
//                        project.getName(),
//                        project.getType(),
//                        project.getTeamSize(),
//                        project.getBudget(),
//                        project.getDescription());
//            }
//        }

        projectService.viewByBudgetRange(minimumBudgetRange, maximumBudgetRange);
    }

    private void viewProjectByType(ProjectType type) {
        projectService.viewByType(type);
//        Project[] projects = projectService.getAllProjects();
//
//        final int ROW_WIDTH = 132;
//        Util.displayTableHeader(
//                ROW_WIDTH,
//                "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
//                "ID",
//                "PROJECT NAME",
//                "TYPE",
//                "TEAM SIZE",
//                "BUDGET",
//                "DESCRIPTION");
//
//        Arrays
//                .stream(projects)
//                .filter(project -> project.getType() == type)
//                .forEach(project -> Util.displayTableRow(
//                        ROW_WIDTH,
//                        "| %-4s | %-26s | %-15s | %-15s | %-15s | %-40s |",
//                        project.getId(),
//                        project.getName(),
//                        project.getType(),
//                        project.getTeamSize(),
//                        project.getBudget(),
//                        project.getDescription()));
    }

    private void viewAllProjects() {
//        projectService.displayAllProjects();

        projectService.viewProjects();

        boolean running = true;
        Project project;
        do {
            try {
                Util.displayAsPrompt("\nEnter project ID to view details (or 0 to return)");

                String projectId = scanner.nextLine();

                if (projectId.equals("0")) {
                    return;
                }

                ValidationUtils.validateProjectID(projectId);

//                project = projectService.getProjectById(input);
                project = projectService.getProjectRepository().getById(projectId);
//                Util.displayAsHeading(String.format("Project Details: %s", projectId.toUpperCase()));
//
//                projectService.viewProject(projectId);

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
    }

    private void projectDetailsMenuForRegularUser(Project project) {
        ArrayList<String> menuItems = new ArrayList<>();

        menuItems.add("Add new task");
        menuItems.add("Update task status");
        menuItems.add("Back to Main Menu");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading(String.format("Project Details: %s", project.getId()));

                projectService.viewProject(project.getId());

                Util.displayAsOption("Options", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask();
                        break;
                    case "2":
                        updateTaskStatus();
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

                projectService.viewProject(project.getId());

                Util.displayAsOption("Options", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask();
                        break;
                    case "2":
                        updateTaskStatus();
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
        Util.displayAsHeading("REMOVE TASK BY ID");

        boolean valid = false;
        Task task = null;
        do {
            try {
                Util.displayAsPrompt("Welcome, to delete a task please enter its ID here (or Q to return)");
                String taskId = scanner.nextLine();

                if (taskId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateTaskID(taskId);

                Util.displayAsPrompt("\nEnter ID of project associated with this task");
                String projectId = scanner.nextLine();

                if (projectId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateProjectID(projectId);

                task = taskService.removeTask(projectId, taskId);
                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);

        Util.displayText(
                String.format(
                        "\nTask '%s' has been deleted successfully\n",
                        task.getName()));
    }

    private void addNewTask() throws ProjectNotFoundException {
        Util.displayAsHeading("ADD NEW TASK");

        boolean valid = false;
        String projectId;
        Project project = null;
        do {
            Util.displayAsPrompt("Enter assigned project ID");
            projectId = scanner.nextLine();

            try {
                ValidationUtils.validateProjectID(projectId);

                project = projectService.getProjectRepository().getById(projectId);

                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);

        String taskName = null;
        valid = false;
        do {
            try {
                Util.displayAsPrompt("\nEnter task name");
                String input = scanner.nextLine();

                if (project.getTasks().get(task -> task.getName().equalsIgnoreCase(input)) != null) {
                    throw new Exception("Duplicate task name found. Change task name to continue.");
                }

                taskName = input;
                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);

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

        taskService.addNewTask(
                projectId,
                new Task(taskName, taskStatus, projectId));

        Util.displayText(String.format(
                "\nTask '%s' added successfully to Project %s\n",
                taskName,
                projectId));
    }

    private void updateTaskStatus() throws TaskNotFoundException, ProjectNotFoundException {
        Util.displayAsHeading("UPDATE TASK STATUS");

        boolean valid = false;
        Task task = null;
        Project project = null;
        do {
            try {
                Util.displayAsPrompt("\nEnter task ID (or Q to quit)");
                String taskId = scanner.nextLine();

                if (taskId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateTaskID(taskId);

                Util.displayAsPrompt("\nEnter ID of project associated with this task");
                String projectId = scanner.nextLine();

                if (projectId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateProjectID(projectId);

                project = projectService.getProjectRepository().getById(projectId);
                task = project.getTasks().get(t -> t.getId().equalsIgnoreCase(taskId));
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
                    taskService.updateTaskStatus(project.getId(), task.getId(), TaskStatus.PENDING);
                    valid = true;
                    break;
                case "I":
                    taskService.updateTaskStatus(project.getId(), task.getId(), TaskStatus.IN_PROGRESS);
                    valid = true;
                    break;
                case "C":
                    taskService.updateTaskStatus(project.getId(), task.getId(), TaskStatus.COMPLETED);
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
        boolean exit = false;
        do {
            try {
                Util.displayAsHeading("PROJECT MANAGEMENT MENU");

                ArrayList<String> menuItems = new ArrayList<>();

                menuItems.add("Create New Project");
                menuItems.add("View Project Catalog");
                menuItems.add("Back to Main Menu");

                Util.displayAsOption("Project Management Menu", menuItems);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        createNewProject();
                        break;
                    case "2":
                        projectCatalog();
                        break;
                    case "3":
                        exit = true;
                        break;
                    default:
                        throw new Exception("Invalid input. Please choose an option between 1 and 3");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!exit);
    }

    private void createNewProject() {
        try {
            if (user.getRole() != UserRole.ADMIN) {
                Util.displayAsError("Access denied. Only Admins can create projects");
                return;
            }

            Util.displayAsHeading("Create a new Project");

            Util.displayText("Enter the project details below to create a new one");

            Util.displayAsPrompt("\nEnter project name");
            String name = scanner.nextLine();

            double budget = -1;
            do {
                try {
                    Util.displayAsPrompt("\nEnter budget amount");
                    budget = Double.parseDouble(scanner.nextLine());

                    if (budget <= 0) {
                        throw new Exception("Please enter an amount greater than 0");
                    }
                } catch (Exception e) {
                    Util.displayAsError(e.getMessage());
                }
            } while (budget <= 0);

            int teamSize = 0;
            do {
                try {
                    Util.displayAsPrompt("\nEnter team size (>=1)");
                    teamSize = Integer.parseInt(scanner.nextLine());

                    if (teamSize <= 0) {
                        throw new Exception("Please an amount greater than 0");
                    }
                } catch (Exception e) {
                    Util.displayAsError(e.getMessage());
                }
            } while (teamSize <= 0);

            Util.displayAsPrompt("\nEnter description");
            String description = scanner.nextLine();

            boolean valid;
            Project project = null;
            do {
                Util.displayAsPrompt("\nEnter the project type(SOFTWARE (S) /HARDWARE (H))");
                String type = scanner.nextLine().toUpperCase();

                switch (type) {
                    case "S":
                        project = new SoftwareProject(name, description, teamSize, budget, new ListBasedTaskRepository());
                        valid = true;
                        break;
                    case "H":
                        double materialCost = 0;
                        do {
                            try {
                                Util.displayAsPrompt("\nEnter material cost (>=1)");
                                materialCost = Integer.parseInt(scanner.nextLine());

                                if (materialCost <= 0) {
                                    throw new Exception("Please enter an amount greater than 1");
                                }
                            } catch (Exception e) {
                                Util.displayAsError(e.getMessage());
                            }

                        } while (materialCost <= 0);

                        project = new HardwareProject(name, description, teamSize, budget, materialCost, new ListBasedTaskRepository());
                        valid = true;
                        break;
                    default:
                        Util.displayAsError("`Project type must be either HARDWARE OR SOFTWARE");
                        valid = false;
                }
            } while (!valid);

            projectService.addNewProject(project);
            Util.displayText("\nProject added successfully");
        } catch (Exception e) {
            Util.displayAsError(e.getMessage());
        }
    }
}