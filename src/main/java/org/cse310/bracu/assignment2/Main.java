package org.cse310.bracu.assignment2;


import org.cse310.bracu.assignment2.repository.ConnectionPool;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var dbConnections = ConnectionPool.getInstance();
        System.out.println("Nice");
        dbConnections.closeAllConnections();
        System.out.println("Done");
    }
}
