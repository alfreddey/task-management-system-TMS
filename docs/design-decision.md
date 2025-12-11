# DESIGN DECISION 
## 1. Separation of Concerns and Encapsulation
The project uses classes to logically organize entities such as **User**, **Project**, **Task**, etc. Each class defines its attributes and behaviours alongside specifying their access specifiers. The project also restricts user code from directly accessing fields (by declaring them as private fields) to prevent unexpected updates, but provides access to fields by defining public setter and getter methods. For example, the **Project** class declares private fields, and public getters and setters that access and modify these fields.

## 2. Inheritance
The project leverages abstract classes to define common attributes and behaviours shared by logically related classes. The **User** class, for instance, defines common attributes such as name, role, etc. that is shared by both **AdminUser** and **RegularUser**. By defining **User** as an abstract class, both **AdminUser** and **RegularUser** can extend this class and include their own properties.

## 3. Polymorphism
The project uses polymorphism to redefine certain class methods to promote code reusability. For example, the **Project** abstract class defines an abstract method (**getProjectDetails**) which is overridden and implemented by its concrete classes. The **SoftwareProject** class, for instance, overrides this method to return its attributes, likewise, the **HardwareProject** class. This ensures that these two classes inherit the same method but define custom implementations.