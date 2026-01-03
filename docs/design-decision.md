# DESIGN DECISION

## 1. Separation of Concerns and Encapsulation
The system uses the **Service-Repository-View** pattern, separating business logic, data storage, and UI. Fields are **private** to prevent direct access, with public methods for safe interaction. For example, **ListBasedProjectRepository** manages project storage via encapsulated methods.

## 2. Abstraction and Interfaces
Interfaces define contracts for components, allowing flexible implementations. **ProjectRepository** and **TaskRepository** handle CRUD operations, while **ProjectView** and **TaskView** manage display. Concrete classes like **ArrayBasedProjectRepository** or **TableBasedTaskView** implement these without altering service logic.

## 3. Composition and Modular Relationships
Services compose their repositories and views, shown in UML as diamond-headed arrows. **ProjectService** uses **ProjectRepository**, **ProjectView**, and **ReportCalculator** to coordinate operations, keeping modules independent yet integrated.

## 4. Polymorphism and Flexibility
Polymorphism allows multiple implementations, e.g., **DefaultReportCalculator** implements **ReportCalculator** methods like **calculateAverageCompletionRate()**. Storage mechanisms (List vs. Array) can be swapped without changing service logic.

## 5. Consistency and UI Standards
Table-based views use a private constant **ROW_WIDTH: int** for uniform formatting. Methods like **viewProjects()** and **viewTasks()** standardize data display across modules.
