package org.example.services;

import org.example.models.AdminUser;
import org.example.models.RegularUser;
import org.example.models.User;
import org.example.utils.exceptions.UserNotFoundException;

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

    public void addUser(User user) throws Exception {
        if (userCount >= MAX_USERS - 1) {
            throw new Exception("User array is full");
        }

        users[userCount++] = user;
    }

    // public User addAdminUser(String name, String email) throws Exception {
    // var user = new AdminUser(name, email);
    // addUser(user);
    // return user;
    // }

    // public User addRegularUser(String name, String email) throws Exception {
    // var user = new RegularUser(name, email);
    // addUser(user);
    // return user;
    // }

    public void addAdminUser(String name, String email) throws Exception {
        addUser(new AdminUser(name, email));
    }

    public void addRegularUser(String name, String email) throws Exception {
        addUser(new RegularUser(name, email));
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        throw new UserNotFoundException("User not found");
    }
}
