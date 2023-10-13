package org.cse310.bracu.assignment2.service;

import org.cse310.bracu.assignment2.entities.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final Connection connection;

    public CourseService(Connection connection) {
        this.connection = connection;
    }

    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Course (courseID, courseCode, totalCapacity, currentNumber, version) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseID());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setInt(3, course.getTotalCapacity());
            pstmt.setInt(4, course.getCurrentNumber());
            pstmt.setInt(5, course.getVersion());
            pstmt.executeUpdate();
        }
    }

    public void removeCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Course WHERE courseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            pstmt.executeUpdate();
        }
    }

    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Course SET courseCode = ?, totalCapacity = ?, currentNumber = ?, version = ? WHERE courseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setInt(2, course.getTotalCapacity());
            pstmt.setInt(3, course.getCurrentNumber());
            pstmt.setInt(4, course.getVersion());
            pstmt.setString(5, course.getCourseID());
            pstmt.executeUpdate();
        }
    }

    public void addLecturerToCourse(String courseId, String lecturerId) throws SQLException {
        String sql = "INSERT INTO Lecturer_Course (lecturerId, courseId) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, lecturerId);
            pstmt.setString(2, courseId);
            pstmt.executeUpdate();
        }
    }

    public void removeLecturerFromCourse(String courseId, String lecturerId) throws SQLException {
        String sql = "DELETE FROM Lecturer_Course WHERE lecturerId = ? AND courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, lecturerId);
            pstmt.setString(2, courseId);
            pstmt.executeUpdate();
        }
    }

    public List<Course> findAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Course course = new Course(rs.getString("courseID"), rs.getString("courseCode"),
                        rs.getInt("totalCapacity"), rs.getInt("currentNumber"),
                        null, null, null,
                        rs.getInt("version"));
                courses.add(course);
            }
        }
        return courses;
    }
}

