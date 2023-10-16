package org.cse310.bracu.assignment2.service;

import org.cse310.bracu.assignment2.entities.Course;
import org.cse310.bracu.assignment2.entities.Lecturer;
import org.cse310.bracu.assignment2.entities.Schedule;
import org.cse310.bracu.assignment2.entities.Student;
import org.cse310.bracu.assignment2.exceptions.OptimisticLockException;
import org.cse310.bracu.assignment2.repository.ConnectionPool;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseService {
    private final Connection connection;
    private static CourseService courseService;

    private CourseService(Connection connection) {
        this.connection = connection;
    }
    public static CourseService getInstance() throws SQLException {
        if (courseService == null) {
            courseService = new CourseService(ConnectionPool.getInstance().getConnection());
        }
        return courseService;
    }
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Course (courseID, courseCode, section, totalCapacity," +
                " availableSeat, version) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, course.getCourseID());
            prepareStatement.setString(2, course.getCourseCode());
            prepareStatement.setString(3, course.getSection());
            prepareStatement.setInt(4, course.getTotalCapacity());
            prepareStatement.setInt(5, course.getAvailableSeat());
            prepareStatement.setInt(6, 1);
            prepareStatement.executeUpdate();
        }
        course.getSchedule().forEach((schedule)-> {
            try {
                addScheduleToCourse(course.getCourseID(), schedule);
            } catch (SQLException e) {
                throw new RuntimeException("I am lazy and I don't want to implement transaction xD");
            }
        });
    }

    public void addScheduleToCourse(String courseId, Schedule schedule) throws SQLException {
        String sql = "INSERT INTO Schedule (day, startTime, endTime, version, courseId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, schedule.getDay().name());
            prepareStatement.setTime(2, Time.valueOf(schedule.getStartTime()));
            prepareStatement.setTime(3, Time.valueOf(schedule.getEndTime()));
            prepareStatement.setInt(4, schedule.getVersion());
            prepareStatement.setString(5, courseId);
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
        String sql = "UPDATE Course SET courseCode = ?, totalCapacity = ?," +
                " availableSeat = ?, version = version + 1" +
                " WHERE courseID = ? AND version = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, course.getCourseCode());
            prepareStatement.setInt(2, course.getTotalCapacity());
            prepareStatement.setInt(3, course.getAvailableSeat());
            prepareStatement.setString(4, course.getCourseID());
            prepareStatement.setInt(5, course.getVersion());
            int affectedRows = prepareStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new OptimisticLockException("Update failed, no rows affected. Version mismatch.");
            }
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
                Course course = new Course(rs.getString("courseID"),
                        rs.getString("courseCode"),
                        rs.getString("section"),
                        rs.getInt("totalCapacity"),
                        rs.getInt("availableSeat"),
                        getSchedulesByCourseId(rs.getString("courseID")),
                        getStudentsByCourseId(rs.getString("courseID")),
                        getLecturersByCourseId(rs.getString("courseID")),
                        rs.getInt("version"));
                courses.add(course);
            }
        }
        return courses;
    }

    private List<Schedule> getSchedulesByCourseId(String courseId) throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM Schedule WHERE courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule(DayOfWeek.valueOf(rs.getString("day")),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getInt("version"));
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    private Set<Student> getStudentsByCourseId(String courseId) throws SQLException {
        Set<Student> students = new HashSet<>();
        String sql = "SELECT User.*, Student.studentId FROM User" +
                " JOIN Student ON User.userId = Student.userId" +
                " JOIN Student_Course ON Student.userId = Student_Course.studentId" +
                " WHERE Student_Course.courseId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, courseId);
            ResultSet queryResult = prepareStatement.executeQuery();
            while (queryResult.next()) {
                Student student = new Student(queryResult.getString("userId"),
                        queryResult.getString("name"),
                        queryResult.getString("email"),
                        queryResult.getString("encryptedPassword"),
                        queryResult.getString("studentId"),
                        new HashSet<>(), queryResult.getInt("version"));
                students.add(student);
            }
        }
        return students;
    }

    private Set<Lecturer> getLecturersByCourseId(String courseId) throws SQLException {
        Set<Lecturer> lecturers = new HashSet<>();
        String sql = "SELECT User.*, Lecturer.lecturerId FROM User" +
                " JOIN Lecturer ON User.userId = Lecturer.userId" +
                " JOIN Lecturer_Course ON Lecturer.userId = Lecturer_Course.lecturerId" +
                " WHERE Lecturer_Course.courseId = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, courseId);
            ResultSet queryResult = prepareStatement.executeQuery();
            while (queryResult.next()) {
                Lecturer lecturer = new Lecturer(queryResult.getString("userId"),
                        queryResult.getString("name"),
                        queryResult.getString("email"),
                        queryResult.getString("encryptedPassword"),
                        queryResult.getString("lecturerId"),
                        new HashSet<>());
                lecturers.add(lecturer);
            }
        }
        return lecturers;
    }
}
