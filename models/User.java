package models;

public abstract class User {
    private static int index = 0;
    private String id;
    private String name;
    private String email;
    private UserRole role;

    public User(String name, String email, UserRole role) {
        index += 1;
        this.id = String.format("U%03d", index);
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}