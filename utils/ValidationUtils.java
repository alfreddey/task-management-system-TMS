package utils;

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
}
