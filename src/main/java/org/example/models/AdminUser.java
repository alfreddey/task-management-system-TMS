package org.example.models;

import org.example.models.UserRole;

public class AdminUser extends User {
    public AdminUser(String name, String email) {
        super(name, email, UserRole.ADMIN);
    }
}