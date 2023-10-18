package org.cse310.bracu.assignment2.entities;

import java.util.Set;

public class Lecturer extends User {
    private final String lecturerId;
    private Set<Course> courses;

    public Lecturer(String userId, String name,
                    String email, String encryptedPassword,
                    String lecturerId, Set<Course> courses) {
        super(userId, name, email, encryptedPassword, UserType.LECTURER);
        this.lecturerId = lecturerId;
        this.courses = courses;
    }

    public String getLecturerId() {
        return this.lecturerId;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
