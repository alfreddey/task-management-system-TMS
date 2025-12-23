package org.example.utils;

import org.example.utils.exceptions.InvalidProjectIDException;
import org.example.utils.exceptions.InvalidTaskIDException;

import java.util.regex.Pattern;

public class ValidationUtils {
    public static void validateEmail(String email) throws Exception {
        var pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(email);

        if (!matcher.find()) {
            throw new Exception("Invalid email format. Please enter a valid email (e.g. al@gmail.com).");
        }
    }

    public static void validateProjectID(String projectID) throws InvalidProjectIDException {
        var pattern = Pattern.compile("^P\\d{3}\\b", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(projectID);

        if (!matcher.find()) {
            throw  new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
        }
    }

    public static void validateTaskID(String taskID) throws InvalidTaskIDException {
        var pattern = Pattern.compile("^T\\d{3}\\b", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(taskID);

        if (!matcher.find()) {
            throw  new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
        }
    }
}
