package org.example.models;

import org.example.models.UserRole;

public class RegularUser extends User {
    public RegularUser(String name, String email) {
        super(name, email, UserRole.REGULAR);
    };
}