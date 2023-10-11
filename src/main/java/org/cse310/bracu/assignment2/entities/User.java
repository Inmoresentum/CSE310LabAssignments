package org.cse310.bracu.assignment2.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String encryptedPassword;
    private UserType userType;

    public User(String userId, String name, String email, String encryptedPassword, UserType userType) {
        this.userId = userId;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.userType = userType;
    }
}
