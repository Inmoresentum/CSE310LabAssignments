package org.cse310.bracu.assignment2.entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User {
    private String StudentID;
    private Set<Course> takenCourse;
    private Integer version;

    public Student(String userId, String name, String email,
                   String encryptedPassword, String studentID,
                   Set<Course> takenCourse, Integer version) {
        super(userId, name, email, encryptedPassword, UserType.STUDENT);
        StudentID = studentID;
        this.takenCourse = takenCourse;
        this.version = version;
    }
}
