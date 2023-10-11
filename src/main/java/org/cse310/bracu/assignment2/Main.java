package org.cse310.bracu.assignment2;


import org.cse310.bracu.assignment2.entities.Student;
import org.cse310.bracu.assignment2.repository.ConnectionPool;
import org.cse310.bracu.assignment2.service.StudentService;

import java.sql.SQLException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws SQLException {
        var dbConnections = ConnectionPool.getInstance();
        System.out.println("Nice");
        StudentService studentService = new StudentService(dbConnections.getConnection());
        var createdStudent = new Student("202343343", "whatever", "whatever@mail.com", "oki dokie", "2034u343", new HashSet<>(), 1);
        studentService.addStudent(createdStudent);
        System.out.println("Done creating the student");
        studentService.findAllStudents().forEach(System.out::println);
        dbConnections.closeAllConnections();
        System.out.println("Done");
    }
}
