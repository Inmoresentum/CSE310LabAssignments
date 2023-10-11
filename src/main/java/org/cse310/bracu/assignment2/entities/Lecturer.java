package org.cse310.bracu.assignment2.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Lecturer extends User {
    private final String lecturerId;
    private Set<Course> courses;

    public Lecturer(String userId, String name, String email, String encryptedPassword, String lecturerId, Set<Course> courses) {
        super(userId, name, email, encryptedPassword, UserType.LECTURER);
        this.lecturerId = lecturerId;
        this.courses = courses;
    }
}
