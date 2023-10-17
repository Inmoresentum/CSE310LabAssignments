package org.cse310.bracu.assignment2.entities;


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

    public String getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String toString() {
        return "User(userId=" + this.getUserId() + ", name=" + this.getName() +
                ", email=" + this.getEmail() + ", encryptedPassword=" +
                this.getEncryptedPassword() + ", userType=" + this.getUserType() + ")";
    }
}
