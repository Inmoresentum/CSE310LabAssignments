package org.cse310.bracu.assignment2.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static ConnectionPool instance;
    private final List<Connection> connectionPool;

    private ConnectionPool() throws SQLException {
        connectionPool = new ArrayList<>();
        initializeDB();
        for (int i = 0; i < 5; i++) {
            Connection conn = DriverManager
                    .getConnection("jdbc:mariadb://localhost:3306/CSE310DB",
                            "root", "");
            connectionPool.add(conn);
        }
    }

    private void initializeDB() throws SQLException {
        connectionPool.add(DriverManager
                .getConnection("jdbc:mariadb://localhost:3306/CSE310DB",
                        "root", ""));
        executeStartupScripts();
        closeAllConnections();
    }

    public static synchronized ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            throw new RuntimeException("No available connections");
        }
        return connectionPool.remove(connectionPool.size() - 1);
    }

    public synchronized void releaseConnection(Connection conn) {
        connectionPool.add(conn);
    }

    public synchronized void closeAllConnections() throws SQLException {
        for (Connection conn : connectionPool) {
            conn.close();
        }
        connectionPool.clear();
        System.out.println("Cleared all connections");
    }

    private void executeStartupScripts() throws SQLException {
        removeEveryThingAndInitializeCleanDB();
        createNecessarySchemas();
    }

    private void removeEveryThingAndInitializeCleanDB() throws SQLException {
        String firstSQL = "DROP DATABASE IF EXISTS CSE310DB";
        String secondSql = "CREATE DATABASE IF NOT EXISTS CSE310DB;";

        Connection conn = getConnection();
        var statement = conn.createStatement();
        statement.execute(firstSQL);
        statement.execute(secondSql);
        releaseConnection(conn);

    }

    private void createNecessarySchemas() throws SQLException {
        for (var connection : connectionPool) {
            System.out.println(connection.isClosed());
        }
        String[] queries = {
                "CREATE TABLE CSE310DB.User (userId            VARCHAR(255) PRIMARY KEY," +
                        " name              VARCHAR(255)        NOT NULL," +
                        " email             VARCHAR(255) UNIQUE NOT NULL," +
                        " encryptedPassword VARCHAR(255)        NOT NULL," +
                        " userType          ENUM('STUDENT'," +
                        " 'LECTURER') NOT NULL);",
                "CREATE TABLE CSE310DB.Student" +
                        "(" +
                        "    userId    VARCHAR(255) PRIMARY KEY," +
                        "    studentId VARCHAR(255) UNIQUE NOT NULL," +
                        "    version   INT," +
                        "    FOREIGN KEY (userId) REFERENCES User (userId)" +
                        ");",

                "CREATE TABLE CSE310DB.Lecturer" +
                        "(" +
                        "userId     VARCHAR(255) PRIMARY KEY," +
                        "lecturerId VARCHAR(255) UNIQUE NOT NULL," +
                        "FOREIGN KEY (userId) REFERENCES User (userId)" +
                        ");",

                "CREATE TABLE CSE310DB.Course" +
                        "(" +
                        "courseID      VARCHAR(255) PRIMARY KEY," +
                        "courseCode    VARCHAR(255) NOT NULL," +
                        "section       VARCHAR(255) NOT NULL," +
                        "totalCapacity INT          NOT NULL," +
                        "availableSeat INT          NOT NULL," +
                        "version       INT," +
                        "UNIQUE (courseCode, section)" +
                        ");",

                "CREATE TABLE CSE310DB.Student_Course" +
                        "(" +
                        "studentId VARCHAR(255)," +
                        "courseId  VARCHAR(255)," +
                        "FOREIGN KEY (studentId) REFERENCES Student (userId)," +
                        "FOREIGN KEY (courseId) REFERENCES Course (courseID)," +
                        "PRIMARY KEY (studentId, courseId)" +
                        ");",

                "CREATE TABLE CSE310DB.Lecturer_Course" +
                        "(" +
                        "lecturerId VARCHAR(255)," +
                        "courseId   VARCHAR(255)," +
                        "FOREIGN KEY (lecturerId) REFERENCES Lecturer (userId)," +
                        "FOREIGN KEY (courseId) REFERENCES Course (courseID)," +
                        "PRIMARY KEY (lecturerId, courseId)" +
                        ");",

                "CREATE TABLE CSE310DB.Schedule" +
                        "(" +
                        "scheduleID INT AUTO_INCREMENT PRIMARY KEY," +
                        "day        ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL," +
                        "startTime  TIME NOT NULL," +
                        "endTime    TIME NOT NULL," +
                        "version    INT," +
                        "courseId   VARCHAR(255)," +
                        "FOREIGN KEY (courseId) REFERENCES Course (courseID)" +
                        ");"
        };
        Connection connection = getConnection();
        var statement = connection.createStatement();
        for (var query : queries) {
            statement.execute(query);
        }
        releaseConnection(connection);
        System.out.println("Trying to check some stuff");
        for (var conn : connectionPool) {
            System.out.println(conn.isClosed());
        }
    }

    public void printStatusOfDBConnections() {
        connectionPool.forEach(connection -> {
            try {
                System.out.println(connection.isClosed());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
