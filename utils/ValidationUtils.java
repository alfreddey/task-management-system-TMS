package utils;

import java.util.stream.IntStream;

public class ValidationUtils {
    public static void validateProjectID(String projectID) throws Exception {
        final int MAX_PROJECT_ID_LENGTH = 4;
        int projectIdLength = projectID.length();

        if ((projectID.charAt(0) != 'P'
                && projectID.charAt(0) != 'p')
                || projectIdLength != MAX_PROJECT_ID_LENGTH) {
            throw new Exception("Invalid input, please enter a valid ID (e.g., P001).");
        }

        for (int i = 1; i < projectIdLength; i++) {
            var character = projectID.charAt(i);

            if (!Character.isDigit(character)) {
                throw new Exception("Invalid input, please enter a valid ID (e.g., P001).");
            }
        }
    }

    public static boolean isValidProjectID(String input, int maxLength) {
        int inputLength = input.length();
        input = input.toUpperCase();

        if (input.charAt(0) == 'P' && inputLength != maxLength) {
            return false;
        }

        for (int i = 1; i < inputLength; i++) {
            var character = input.charAt(i);

            if (!Character.isDigit(character)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidProjectID(String input) {
        return isValidProjectID(input, 4);
    }

    public static int getValidInt(String input, int min, int max) {
        try {
            int value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                return value;
            } else {
                Util.displayError("X Error: Input out of range. Must be between " + min + " and " + max + ".");
                return -1;
            }
        } catch (NumberFormatException e) {
            Util.displayError("X Error: Invalid input. Please enter a valid number.");
            return -1;
        }
    }

    public static double getValidDouble(String input) {
        try {
            double value = Double.parseDouble(input);

            if (value >= 0) {
                return value;
            } else {
                Util.displayError("X Error: Input out of range. Must be between greater than or equal to 0");
                return -1;
            }
        } catch (NumberFormatException e) {
            Util.displayError("X Error: Invalid input. Please enter a valid number.");
            return -1;
        }
    }
}
