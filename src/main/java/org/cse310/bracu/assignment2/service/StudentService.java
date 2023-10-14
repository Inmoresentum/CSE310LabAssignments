package org.cse310.bracu.assignment2.service;

import org.cse310.bracu.assignment2.entities.Course;
import org.cse310.bracu.assignment2.entities.Student;
import org.cse310.bracu.assignment2.entities.UserType;

import java.sql.*;
import java.util.*;

public class StudentService {
    private final Connection connection;

    public StudentService(Connection connection) {
        this.connection = connection;
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO User (userId, name, email, encryptedPassword, userType) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, student.getUserId());
            prepareStatement.setString(2, student.getName());
            prepareStatement.setString(3, student.getEmail());
            prepareStatement.setString(4, student.getEncryptedPassword());
            prepareStatement.setString(5, UserType.STUDENT.name());
            prepareStatement.executeUpdate();
        }

        sql = "INSERT INTO Student (userId, studentId, version) VALUES (?, ?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, student.getUserId());
            prepareStatement.setString(2, student.getStudentID());
            prepareStatement.setInt(3, student.getVersion());
            prepareStatement.executeUpdate();
        }
    }

    public Optional<Student> findStudentById(String userId) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE User.userId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, userId);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.next()) {
                var student = new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
                student.setTakenCourse(getCoursesByStudentId(student.getStudentID()));
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public Optional<Student> findStudentByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE User.email = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, email);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.next()) {
                var student = new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
                student.setTakenCourse(getCoursesByStudentId(student.getStudentID()));
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public Optional<Student> findStudentByStudentId(String studentId) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE Student.studentId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, studentId);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.next()) {
                var student = new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
                student.setTakenCourse(getCoursesByStudentId(studentId));
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    private Set<Course> getCoursesByStudentId(String studentId) throws SQLException {
        Set<Course> courses = new HashSet<>();
        String sql = "SELECT Course.* FROM Course JOIN Student_Course" +
                " ON Course.courseID = Student_Course.courseId" +
                " WHERE Student_Course.studentId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, studentId);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Course course = new Course(rs.getString("courseID"), rs.getString("courseCode"),
                        rs.getInt("totalCapacity"), rs.getInt("availableSeat"),
                        null, null, null, rs.getInt("version"));
                courses.add(course);
            }
        }
        return courses;
    }

    public List<Student> findAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                var student = new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
                student.setTakenCourse(getCoursesByStudentId(student.getStudentID()));
                students.add(student);
            }
        }
        return students;
    }

    public void updateStudentName(String userId, String newName) throws SQLException {
        String sql = "UPDATE User SET name = ? WHERE userId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, newName);
            prepareStatement.setString(2, userId);
            prepareStatement.executeUpdate();
        }
    }

    public void updateStudentEmail(String userId, String newEmail) throws SQLException {
        String sql = "UPDATE User SET email = ? WHERE userId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, newEmail);
            prepareStatement.setString(2, userId);
            prepareStatement.executeUpdate();
        }
    }

    public void addCourseToStudent(String studentId, String courseId) throws SQLException {
        String sql = "INSERT INTO Student_Course (studentId, courseId) VALUES (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, studentId);
            prepareStatement.setString(2, courseId);
            prepareStatement.executeUpdate();
        }
    }

    public void removeCourseFromStudent(String studentId, String courseId) throws SQLException {
        String sql = "DELETE FROM Student_Course WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, studentId);
            prepareStatement.setString(2, courseId);
            prepareStatement.executeUpdate();
        }
    }
}

