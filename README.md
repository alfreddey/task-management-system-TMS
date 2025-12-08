# TASK MANAGEMENT SYSTEM

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/alfreddey/task-management-system.git
   cd task-management-system
   ```

2. **Prerequisites:**
   - Ensure you have Java installed (recommended: JDK 11 or higher).
   - You may also need an IDE such as IntelliJ IDEA (project includes `.iml` files and `.idea` directory).
   - If the project uses Maven or Gradle, ensure those are installed.

3. **Install Dependencies:**
   - If using Maven:
     ```bash
     mvn install
     ```
   - If using Gradle:
     ```bash
     gradle build
     ```

## Run Instructions

1. **Using an IDE (recommended):**
   - Open the project in IntelliJ IDEA or another Java IDE.
   - Use the IDE's run functionality to start the application (typically, the main class is found in `src/`).

2. **Using Command Line:**
   - If using Maven:
     ```bash
     mvn exec:java
     ```
   - If using Gradle:
     ```bash
     gradle run
     ```
   - If compiled manually:
     ```bash
     javac -d out src/**/*.java
     java -cp out <MainClass>
     ```
     Replace `<MainClass>` with your actual main class name (e.g., `com.example.Main`).

## Additional Notes

- Documentation for API and further details may be available in the `docs/` directory.
- For customization or contributing, refer to the existing `README.md`.
