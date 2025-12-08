package org.example.services;

import org.example.models.AdminUser;
import org.example.models.RegularUser;
import org.example.models.User;

public class UserService {
    private static UserService service;
    private final int MAX_USERS = 999;
    private int userCount;
    private User[] users;

    private UserService() {
        this.users = new User[MAX_USERS];
        this.userCount = 0;
    }

    public static UserService getService() {
        if (service == null) {
            service = new UserService();
        }

        return service;
    }

    public void addUser(User user) {
        if (userCount >= MAX_USERS - 1) {
            System.out.println("User array is full");
            return;
        }

        users[userCount++] = user;
    }

    public void addAdminUser(String name, String email) {
        addUser(new AdminUser(name, email));
    }

    public void addRegularUser(String name, String email) {
        addUser(new RegularUser(name, email));
    }

    public User getUserByEmail(String email) throws Exception {
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        throw new Exception("User not found");
    }
}
