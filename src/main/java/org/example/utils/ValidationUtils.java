package org.example.utils;

import org.example.utils.exceptions.InvalidProjectIDException;
import org.example.utils.exceptions.InvalidTaskIDException;

import java.lang.reflect.Parameter;
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
        var pattern = Pattern.compile("P\\d{3}", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(projectID);

        if (!matcher.find()) {
            throw  new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
        }
//        final int MAX_PROJECT_ID_LENGTH = 4;
//        int projectIdLength = projectID.length();
//
//        if ((projectID.charAt(0) != 'P'
//                && projectID.charAt(0) != 'p')
//                || projectIdLength != MAX_PROJECT_ID_LENGTH) {
//            throw new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
//        }
//
//        for (int i = 1; i < projectIdLength; i++) {
//            var character = projectID.charAt(i);
//
//            if (!Character.isDigit(character)) {
//                throw new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
//            }
//        }
    }

    public static void validateTaskID(String taskID) throws InvalidTaskIDException {
        var pattern = Pattern.compile("T\\d{3}", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(taskID);

        if (!matcher.find()) {
            throw  new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
        }
//        final int MAX_PROJECT_ID_LENGTH = 4;
//        int projectIdLength = taskID.length();
//
//        if ((taskID.charAt(0) != 'T'
//                && taskID.charAt(0) != 't')
//                || projectIdLength != MAX_PROJECT_ID_LENGTH) {
//            throw new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
//        }
//
//        for (int i = 1; i < projectIdLength; i++) {
//            var character = taskID.charAt(i);
//
//            if (!Character.isDigit(character)) {
//                throw new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
//            }
//        }
    }
}
