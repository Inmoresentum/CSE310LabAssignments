package org.cse310.bracu.assignment2.service;

import org.cse310.bracu.assignment2.entities.Lecturer;
import org.cse310.bracu.assignment2.entities.UserType;
import org.cse310.bracu.assignment2.repository.ConnectionPool;
import org.cse310.bracu.assignment2.security.Session;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class LecturerService {

    private final Connection connection;
    private static LecturerService lecturerService;
    private LecturerService(Connection connection) {
        this.connection = connection;
    }

    public static LecturerService getInstance() throws SQLException {
        if (lecturerService == null) {
            lecturerService = new LecturerService(ConnectionPool
                    .getInstance().getConnection());
        }
        return lecturerService;
    }
    public boolean authenticate(String email, String password) {
        Optional<Lecturer> mayBeLecturer;
        try {
            mayBeLecturer = findLecturerByEmail(email);
        } catch (SQLException e) {
            return false;
        }
        if (mayBeLecturer.isPresent()) {
            var lecturer = mayBeLecturer.get();
            var encryptedPassword = lecturer.getEncryptedPassword();
            if (BCrypt.checkpw(password, encryptedPassword)) {
                Session.setSession(lecturer);
                return true;
            }
        }
        return false;
    }

    public boolean register(String lecturerName, String lecturerEmail, String lecturerPassword, String lecturerId) {
        try {
            if (lecturerExistsByEmail(lecturerEmail))
                return false;
            var encodedPassword = BCrypt.hashpw(lecturerPassword, BCrypt.gensalt());
            var lecturer = new Lecturer(UUID.randomUUID().toString(),
                    lecturerName,
                    lecturerEmail,
                    encodedPassword,
                    lecturerId,
                    new HashSet<>());
            addLecturer(lecturer);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Lecturer> findLecturerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User JOIN Lecturer ON User.userId = Lecturer.userId WHERE User.email = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, email);
            ResultSet queryResult = prepareStatement.executeQuery();
            if (queryResult.next()) {
                var lecturer = new Lecturer(queryResult.getString("userId"),
                        queryResult.getString("name"),
                        queryResult.getString("email"),
                        queryResult.getString("encryptedPassword"),
                        queryResult.getString("lecturerId"),
                        new HashSet<>());
                return Optional.of(lecturer);
            }
        }
        return Optional.empty();
    }

    private void addLecturer(Lecturer lecturer) throws SQLException {
        String sql = "INSERT INTO User (userId, name, email, encryptedPassword, userType) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, lecturer.getUserId());
            prepareStatement.setString(2, lecturer.getName());
            prepareStatement.setString(3, lecturer.getEmail());
            prepareStatement.setString(4, lecturer.getEncryptedPassword());
            prepareStatement.setString(5, UserType.LECTURER.name());
            prepareStatement.executeUpdate();
        }

        sql = "INSERT INTO Lecturer (userId, lecturerId) VALUES (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, lecturer.getUserId());
            prepareStatement.setString(2, lecturer.getLecturerId());
            prepareStatement.executeUpdate();
        }
    }

    private boolean lecturerExistsByEmail(String email) {
        String sql = "SELECT 1 FROM User WHERE email = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, email);
            ResultSet rs = prepareStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
