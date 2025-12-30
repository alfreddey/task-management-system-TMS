package org.example.utils;

import org.example.interfaces.Menu;
import org.example.models.*;
import org.example.repositories.ListBasedTaskRepository;
import org.example.services.ProjectService;
import org.example.services.ReportService;
import org.example.services.TaskService;
import org.example.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MenuV0 implements Menu {
    private Scanner scanner;
    private ProjectService<Project> projectService;
    private TaskService<Project> taskService;
    private UserService userService;
    private ReportService reportService;
    private User user;

    public MenuV0(Scanner scanner, ProjectService<Project> projectService, TaskService<Project> taskService, UserService userService, ReportService reportService) {
        this.scanner = scanner;
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
        this.reportService = reportService;
    }

    @Override
    public void start() {
        try {
            Util.seedDatabase(projectService, userService);
        } catch (Exception e) {
            Util.displayAsError(e.getMessage());
        }

        boolean running = true;
        do {
            Util.displayAsHeading("JAVA TASK MANAGEMENT SYSTEM");

            Util.displayText("Welcome, Enter S to sign in, R to register or Q to quit the program");

            Util.displayAsPrompt("Enter your choice");

            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "S":
                    login();
                    break;
                case "R":
                    register();
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    Util.displayAsError("Invalid input. Please try again.");
            }
        } while (running);
    }

    @Override
    public void login() {
        do {
            try {
                Util.displayAsHeading("SIGN IN PORTAL");

                Util.displayText("Welcome, please enter your email below or Q to return");

                Util.displayAsPrompt("Enter your email here");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q")) return;

                user = userService.getUserByEmail(input);

                Util.displayText(
                        String.format(
                                "\nYou are successfully signed in as %s",
                                Util.capitalizeText(user.getName())));

                if (Objects.requireNonNull(user.getRole()) == UserRole.ADMIN) {
                    mainMenuForAdminUser();
                } else {
                    mainMenu();
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (true);
    }

    @Override
    public void register() {

    }

    public void mainMenu() {
        List<String> options = new ArrayList<>();

        options.add(String.format(
                "View All Projects (%s)",
                projectService.getProjectRepository().size()));
        options.add("View a Project");
        options.add("Browse Projects");
        options.add("Exit");

        boolean running = true;
        do {
            try {
                Util.displayAsHeading("main menu");

                Util.displayAsOption("options", options);

                Util.displayAsPrompt("\nenter option here");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        viewProjects();
                        break;
                    case "2":
                        viewProject(null);
                        break;
                    case "3":
                        browseProjects();
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        throw new Exception("Invalid filter choice. Please choose between 1 and 4");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    }

    public void viewProjects() {
        boolean valid = false;
        do {
            Util.displayAsHeading("project catalog");

            projectService.viewProjects();

            Util.displayAsOption("options", List.of("Browse projects", "View a project", "Exit"));

            Util.displayAsPrompt("\nenter your option");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                browseProjects();
            } else if (choice.equalsIgnoreCase("2")) {
                viewProject(null);
            } else if (choice.equalsIgnoreCase("3")) {
                valid = true;
            }
        } while(!valid);
    }

    public void browseProjects() {
        boolean running = true;
        do {
            try {
                Util.displayAsHeading("browse project");

                Util.displayAsOption("filter options", List.of("Browse software projects", "Browse hardware projects", "Exit"));

                Util.displayAsPrompt("\nenter your choice here");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        viewProjectsByType(ProjectType.SOFTWARE);
                        break;
                    case "2":
                        viewProjectsByType(ProjectType.HARDWARE);
                        break;
                    case "3":
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

    public void viewProjectsByType(ProjectType type) {
        boolean running = true;
        do {
            try {
                Util.displayAsHeading(String.format("%s projects", type));

                projectService.viewByType(type);

                Util.displayAsOption("options", List.of("View a project", "Exit"));

                Util.displayAsPrompt("\nenter your choice here");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        viewProject(null);
                        break;
                    case "2":
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

    public void viewProject(Project project) {
        boolean running = true;
        Project targetProject = project;
        do {
            try {
                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsHeading(String.format("Project details: %s", targetProject.getId()));

                projectService.viewProject(targetProject.getId());

                Util.displayAsOption("option", List.of("Manage Tasks", "exit"));

                Util.displayAsPrompt("\nenter your option here");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        manageTask(targetProject);
                        break;
                    case "2":
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

    private void mainMenuForAdminUser() {
        List<String> options = new ArrayList<>();

        options.add("Manage Projects");
        options.add("Manage Tasks");
        options.add("View Status Reports");
        options.add("Switch User");
        options.add("Exit");

        boolean running = true;
        do {
            try {
                if (user.getRole() != UserRole.ADMIN) {
                    Util.displayAsError("Access denied. Only Admins can access the main menu");
                    mainMenu();
                    return;
                }

                Util.displayAsHeading("Java Task Management System");

                Util.displayText(
                        String.format("Current User: %s (%s)",
                                Util.capitalizeText(
                                        user.getName()),
                                user.getRole()));

                Util.displayAsOption("Main Menu", options);

                Util.displayAsPrompt("\nEnter your choice");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        manageProject();
                        break;
                    case "2":
                        manageTaskForAdminUser(null);
                        break;
                    case "3":
                        viewStatusReport();
                        break;
                    case "4":
                        login();
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

    public void manageTaskForAdminUser(Project project) {
        List<String> options = new ArrayList<>();

        options.add("Add new task");
        options.add("Update task status");
        options.add("Remove task");
        options.add("Back to Main Menu");

        boolean running = true;
        do {
            try {
                Project targetProject = project;

                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsHeading("Manage Tasks");

                taskService.viewTasks(targetProject.getId());

                Util.displayAsOption("Task Menu Options", options);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask(targetProject);
                        break;
                    case "2":
                        updateTaskStatus(targetProject);
                        break;
                    case "3":
                        removeTaskById(targetProject);
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

    public void removeTaskById(Project project) {
        boolean valid = false;
        Task task = null;
        Project targetProject = project;
        do {
            try {
                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsHeading("REMOVE TASK BY ID");

                taskService.viewTasks(targetProject.getId());

                Util.displayAsPrompt("\nWelcome, to delete a task please enter its ID here (or Q to return)");
                String taskId = scanner.nextLine();

                if (taskId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateTaskID(taskId);

                task = taskService.removeTask(targetProject.getId(), taskId);
                valid = true;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (valid);

        Util.displayText(
                String.format(
                        "\nTask '%s' has been deleted successfully\n",
                        task.getName()));
    }

    public void addNewTask(Project project) {
        Util.displayAsHeading("ADD NEW TASK");

        Project targetProject = project;
        String taskName = null;
        boolean valid = false;
        do {
            try {
                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsPrompt("\nEnter task name");
                String input = scanner.nextLine();

                if (targetProject.getTasks().get(task -> task.getName().equalsIgnoreCase(input)) != null) {
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

        try {
            taskService.addNewTask(
                    targetProject.getId(),
                    new Task(taskName, taskStatus, targetProject.getId()));
        } catch (Exception e) {
            Util.displayAsError(e.getMessage());
        }

        Util.displayText(String.format(
                "\nTask '%s' added successfully to Project %s\n",
                taskName,
                targetProject.getId()));
    }

    public void updateTaskStatus(Project project) {
        Util.displayAsHeading("UPDATE TASK STATUS");

        Project targetProject = project;
        boolean valid = false;
        Task task = null;
        do {
            try {
                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsPrompt("\nEnter task ID (or Q to quit)");
                String taskId = Util.capitalizeText(scanner.nextLine());

                if (taskId.equalsIgnoreCase("Q"))
                    return;

                ValidationUtils.validateTaskID(taskId);

                task = targetProject.getTasks().getById(taskId);

                if (task != null) {
                    valid = true;
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage() + ". " + "Please try again");
            }
        } while (!valid);

        valid = false;
        String taskStatus;
        do {
            try {
                Util.displayAsPrompt("\nEnter new status (Pending (P) / In Progress (I) / Completed (C))");
                taskStatus = scanner.nextLine().toUpperCase();

                switch (taskStatus) {
                    case "P":
                        taskService.updateTaskStatus(task.getId(), targetProject.getId(), TaskStatus.PENDING);
                        valid = true;
                        break;
                    case "I":
                        taskService.updateTaskStatus(task.getId(), targetProject.getId(), TaskStatus.IN_PROGRESS);
                        valid = true;
                        break;
                    case "C":
                        taskService.updateTaskStatus(task.getId(), targetProject.getId(), TaskStatus.COMPLETED);
                        valid = true;
                        break;
                    default:
                        Util.displayAsError("Invalid Status. Please choose from Pending / In progress / Completed");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (!valid);

        Util.displayText(
                String.format(
                        "\nTask '%s' marked as %s\n",
                        task.getName(),
                        task.getStatus()));
    }

    public void viewStatusReport() {
        Util.displayAsHeading("PROJECT STATUS REPORT");

        reportService.viewStatusReport();

        Util.displayAsPrompt("\nEnter any key to exit");

        scanner.nextLine();
    }

    public void manageTask(Project project) {
        List<String> options = new ArrayList<>();

        options.add("Add new task");
        options.add("Update task status");
        options.add("Back to Main Menu");

        Project targetProject = project;
        boolean running = true;
        do {
            try {
                if (targetProject == null) {
                    targetProject = getProjectMenu();
                }

                if (targetProject == null) {
                    return;
                }

                Util.displayAsHeading("Manage Tasks");

                taskService.viewTasks(targetProject.getId());

                Util.displayAsOption("Task Menu Options", options);

                Util.displayAsPrompt("\nEnter your choice");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        addNewTask(targetProject);
                        break;
                    case "2":
                        updateTaskStatus(targetProject);
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        throw new Exception("Invalid input, please choose between 1 and 3");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    }

    public Project getProjectMenu() {
        Project targetProject = null;
        boolean valid = true;
        do {
            try {
                Util.displayAsHeading("Project Portal");

                Util.displayAsPrompt("Welcome, Enter a project id here to continue (or Q to quit)");

                String projectId = scanner.nextLine();

                if (projectId.equalsIgnoreCase("Q")) return null;

                ValidationUtils.validateProjectID(projectId);

                targetProject = projectService.getProjectRepository().getById(projectId);

                valid = false;
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (valid);

        return targetProject;
    }

    public void manageProject() {
        boolean running = true;
        do {
            try {
                Util.displayAsHeading("manage project");

                Util.displayAsOption("options", List.of("Create a new project", "View projects", "Exit"));

                Util.displayAsPrompt("\nenter your choice");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        createProject();
                        break;
                    case "2":
                        viewProjects();
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        throw new Exception("");
                }
            } catch (Exception e) {
                Util.displayAsError(e.getMessage());
            }
        } while (running);
    }

    public void createProject() {
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
