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
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, course.getCourseID());
            prepareStatement.setString(2, course.getCourseCode());
            prepareStatement.setInt(3, course.getTotalCapacity());
            prepareStatement.setInt(4, course.getCurrentNumber());
            prepareStatement.setInt(5, course.getVersion());
            prepareStatement.executeUpdate();
        }
    }

    public void removeCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Course WHERE courseID = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, courseId);
            prepareStatement.executeUpdate();
        }
    }

    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Course SET courseCode = ?, totalCapacity = ?, currentNumber = ?, version = ? WHERE courseID = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, course.getCourseCode());
            prepareStatement.setInt(2, course.getTotalCapacity());
            prepareStatement.setInt(3, course.getCurrentNumber());
            prepareStatement.setInt(4, course.getVersion());
            prepareStatement.setString(5, course.getCourseID());
            prepareStatement.executeUpdate();
        }
    }

    public void addLecturerToCourse(String courseId, String lecturerId) throws SQLException {
        String sql = "INSERT INTO Lecturer_Course (lecturerId, courseId) VALUES (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, lecturerId);
            prepareStatement.setString(2, courseId);
            prepareStatement.executeUpdate();
        }
    }

    public void removeLecturerFromCourse(String courseId, String lecturerId) throws SQLException {
        String sql = "DELETE FROM Lecturer_Course WHERE lecturerId = ? AND courseId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, lecturerId);
            prepareStatement.setString(2, courseId);
            prepareStatement.executeUpdate();
        }
    }

    public List<Course> findAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            ResultSet rs = prepareStatement.executeQuery();
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
