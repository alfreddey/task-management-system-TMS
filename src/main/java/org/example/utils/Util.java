package org.example.utils;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.example.services.ProjectService;
import org.example.services.UserService;

public class Util {
    public static void displayTableHeader(int rowWidth, String format, Object... headerTexts) {
        System.out.println("\n" + "+" + "-".repeat(rowWidth) + "+");
        System.out.printf(format, headerTexts);
        System.out.println("\n" + "+" + "-".repeat(rowWidth) + "+");
    }

    public static void displayTableRow(int rowWidth, String format, Object... headerTexts) {
        System.out.printf(format, headerTexts);
        System.out.println("\n" + "-" + "-".repeat(rowWidth) + "-");
    }

    public static void displayText(String text) {
        System.out.println(capitalizeText(text) + "\n");
    }

    public static void displayAsError(String message) {
        System.out.println("\n[ERROR]");
        System.out.println("----");
        System.out.println(capitalizeText(message));
        System.out.println("----\n");
    }

    public static void displayAsPrompt(String text) {
        System.out.print(capitalizeText(text) + ": ");
    }

    public static String capitalizeText(String text) {
        if (text.isEmpty())
            return text;

        String firstCharacter = text.substring(0, 1).toUpperCase();
        String remainingText = text.substring(1);

        return firstCharacter + remainingText;
    }

    public static void displayAsHeading(String text) {
        String line = "+" + "-".repeat(text.length() + 2) + "+";

        System.out.println("\n" + line);
        System.out.println("| " + text.toUpperCase() + " |");
        System.out.println(line + "\n");
    }

    public static void displayAsMenu(String heading, ArrayList<String> menus) {
        System.out.println("\n" + heading);
        System.out.println("-".repeat(heading.length()));

        IntStream
                .range(0, menus.size())
                .forEach(i -> System.out.printf("%d. %s\n", i + 1, menus.get(i)));
    }

    /**
     * Populates the app's user and project list
     */
    public static void seedDatabase(ProjectService projectService, UserService userService) throws Exception {
        userService.addAdminUser("alfred", "a");

        userService.addRegularUser("scar", "r");

        projectService.addSoftwareProject(
                "Azure Project",
                "sdfkj dfa",
                12,
                1324.32);
        projectService.addSoftwareProject(
                "AWS Project",
                "dfa dfa",
                102,
                13240.32);
        projectService.addSoftwareProject(
                "Oracle Project",
                "sdf5grgadfkj dfa",
                42,
                12024.32);

        projectService.addHardwareProject(
                "IT Project",
                "sdf5grgadfkj dfa",
                42,
                12024.32,
                10394);
        projectService.addHardwareProject(
                "Engineer PR",
                "sdf5grgadfkj dfa",
                42,
                12024.32,
                10394);
    }
}