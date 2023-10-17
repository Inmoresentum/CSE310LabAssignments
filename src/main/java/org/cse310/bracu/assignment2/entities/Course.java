package org.cse310.bracu.assignment2.entities;

import java.util.List;
import java.util.Set;

public class Course {
    private String courseID;
    private String courseCode;
    private String section;
    private Integer totalCapacity;
    private Integer availableSeat;
    private List<Schedule> schedule;
    private Set<Student> students;
    private Set<Lecturer> lecturers;
    private Integer version;

    public Course(String courseID, String courseCode, String section, Integer totalCapacity, Integer availableSeat, List<Schedule> schedule, Set<Student> students, Set<Lecturer> lecturers, Integer version) {
        this.courseID = courseID;
        this.courseCode = courseCode;
        this.section = section;
        this.totalCapacity = totalCapacity;
        this.availableSeat = availableSeat;
        this.schedule = schedule;
        this.students = students;
        this.lecturers = lecturers;
        this.version = version;
    }

    public String getCourseID() {
        return this.courseID;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public String getSection() {
        return this.section;
    }

    public Integer getTotalCapacity() {
        return this.totalCapacity;
    }

    public Integer getAvailableSeat() {
        return this.availableSeat;
    }

    public List<Schedule> getSchedule() {
        return this.schedule;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public Set<Lecturer> getLecturers() {
        return this.lecturers;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toString() {
        return "Course(courseID=" + this.getCourseID() + ", courseCode=" + this.getCourseCode() +
                ", section=" + this.getSection() + ", totalCapacity=" + this.getTotalCapacity() +
                ", availableSeat=" + this.getAvailableSeat() + ", schedule=" + this.getSchedule() +
                ", students=" + this.getStudents() + ", lecturers=" + this.getLecturers() +
                ", version=" + this.getVersion() + ")";
    }
}
