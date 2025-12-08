package utils;

import utils.exceptions.InvalidProjectIDException;
import utils.exceptions.InvalidTaskIDException;

public class ValidationUtils {
    public static void validateProjectID(String projectID) throws InvalidProjectIDException {
        final int MAX_PROJECT_ID_LENGTH = 4;
        int projectIdLength = projectID.length();

        if ((projectID.charAt(0) != 'P'
                && projectID.charAt(0) != 'p')
                || projectIdLength != MAX_PROJECT_ID_LENGTH) {
            throw new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
        }

        for (int i = 1; i < projectIdLength; i++) {
            var character = projectID.charAt(i);

            if (!Character.isDigit(character)) {
                throw new InvalidProjectIDException("Invalid input, please enter a valid ID (e.g., P001).");
            }
        }
    }

    public static void validateTaskID(String taskID) throws InvalidTaskIDException {
        final int MAX_PROJECT_ID_LENGTH = 4;
        int projectIdLength = taskID.length();

        if ((taskID.charAt(0) != 'T'
                && taskID.charAt(0) != 't')
                || projectIdLength != MAX_PROJECT_ID_LENGTH) {
            throw new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
        }

        for (int i = 1; i < projectIdLength; i++) {
            var character = taskID.charAt(i);

            if (!Character.isDigit(character)) {
                throw new InvalidTaskIDException("Invalid input, please enter a valid ID (e.g., T001).");
            }
        }
    }
}
