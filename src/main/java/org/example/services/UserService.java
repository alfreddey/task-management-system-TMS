package org.example.services;

import org.example.models.AdminUser;
import org.example.models.RegularUser;
import org.example.models.User;
import org.example.utils.exceptions.UserNotFoundException;

/**
 * Manages operations related to users, including creation, retrieval, and storage.
 * This class uses the Singleton pattern to maintain a single list of users
 * up to a defined maximum capacity.
 */
public class UserService {
    private static UserService service;
    private final int MAX_USERS = 999;
    private int userCount;
    private final User[] users;

  /**
   * Private constructor to prevent direct instantiation, enforcing the Singleton pattern.
   * Initializes the user array and sets the initial user count to zero.
   */
    private UserService() {
        this.users = new User[MAX_USERS];
        this.userCount = 0;
    }

  /**
   * Returns the singleton instance of the UserService.
   * If the instance does not exist, it creates one.
   *
   * @return The single instance of UserService.
   */
    public static UserService getService() {
        if (service == null) {
            service = new UserService();
        }

        return service;
    }

  /**
   * Adds a generic user to the service's user list.
   *
   * @param user The user object to add.
   * @throws Exception if the user array is full (reaches MAX_USERS).
   */
    public void addUser(User user) throws Exception {
        if (userCount >= MAX_USERS - 1) {
            throw new Exception("User array is full");
        }

        users[userCount++] = user;
    }

  /**
   * Creates a new {@code AdminUser} and adds it to the user list.
   *
   * @param name The name of the admin user.
   * @param email The email of the admin user (used as identifier).
   * @return The newly created {@code AdminUser} object.
   * @throws Exception if the user array is full.
   */
    public User addAdminUser(String name, String email) throws Exception {
        var user = new AdminUser(name, email);
        addUser(user);
        return user;
    }

  /**
   * Creates a new {@code RegularUser} and adds it to the user list.
   *
   * @param name The name of the regular user.
   * @param email The email of the regular user (used as identifier).
   * @return The newly created {@code RegularUser} object.
   * @throws Exception if the user array is full.
   */
    public User addRegularUser(String name, String email) throws Exception {
        var user = new RegularUser(name, email);
        addUser(user);
        return user;
    }

  /**
   * Retrieves a user from the list based on their email address.
   *
   * @param email The email of the user to find.
   * @return The {@code User} object matching the email.
   * @throws UserNotFoundException if no user with the specified email is found.
   */
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
