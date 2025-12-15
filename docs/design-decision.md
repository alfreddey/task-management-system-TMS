# DESIGN DECISION

## 1. Separation of Concerns and Encapsulation
The project uses classes to logically organize entities like **User**, **Project**, and **Task**. Each class defines its attributes and behavior using specific access levels. To prevent unintended updates, fields are declared **private**, restricting direct access. Instead, the project provides public **getter and setter** methods to safely view or modify data. For example, the **Project** class uses these methods to manage its internal fields securely.

## 2. Inheritance
The project leverages abstract classes to define common attributes and behaviors shared by related entities. The **User** class, for instance, establishes shared properties like `name` and `role`. By defining **User** as an abstract class, both **AdminUser** and **RegularUser** can extend it to inherit these base properties while adding their own specific functionality.

## 3. Polymorphism
The project uses polymorphism to redefine specific methods, promoting code reusability. The **Project** abstract class defines an abstract method, **getProjectDetails**, which is implemented by its concrete subclasses. For example, **SoftwareProject** and **HardwareProject** both override this method to return their unique data. This ensures both classes share a consistent interface while providing custom implementations.