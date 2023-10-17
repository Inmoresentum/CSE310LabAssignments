package org.cse310.bracu.assignment2.entities;

import java.util.Set;

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

    public String getStudentID() {
        return this.StudentID;
    }

    public Set<Course> getTakenCourse() {
        return this.takenCourse;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    public void setTakenCourse(Set<Course> takenCourse) {
        this.takenCourse = takenCourse;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toString() {
        return "Student(super=" + super.toString() + ", StudentID=" +
                this.getStudentID() + ", takenCourse=" + this.getTakenCourse() +
                ", version=" + this.getVersion() + ")";
    }
}
