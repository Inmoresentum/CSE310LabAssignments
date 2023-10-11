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
        for (int i = 0; i < 5; i++) {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/CSE310DB", "root", "");
            connectionPool.add(conn);
        }
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
    }
}
