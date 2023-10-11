package org.cse310.bracu.assignment2.service;

import org.cse310.bracu.assignment2.entities.Student;
import org.cse310.bracu.assignment2.entities.UserType;

import java.sql.*;
import java.util.HashSet;

public class StudentService {
    private Connection conn;

    public StudentService(Connection conn) {
        this.conn = conn;
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO User (userId, name, email, encryptedPassword, userType) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getUserId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getEncryptedPassword());
            pstmt.setString(5, UserType.STUDENT.name());
            pstmt.executeUpdate();
        }

        sql = "INSERT INTO Student (userId, studentId, version) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getUserId());
            pstmt.setString(2, student.getStudentID());
            pstmt.setInt(3, student.getVersion());
            pstmt.executeUpdate();
        }
    }

    public Student findStudentById(String userId) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE User.userId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
            }
        }
        return null;
    }

    public Student findStudentByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE User.email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
            }
        }
        return null;
    }

    public Student findStudentByStudentId(String studentId) throws SQLException {
        String sql = "SELECT * FROM User JOIN Student ON User.userId = Student.userId WHERE Student.studentId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("encryptedPassword"), rs.getString("studentId"),
                        new HashSet<>(), rs.getInt("version"));
            }
        }
        return null;
    }
}

