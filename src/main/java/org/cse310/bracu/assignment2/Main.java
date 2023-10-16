package org.cse310.bracu.assignment2;


import org.cse310.bracu.assignment2.entities.Course;
import org.cse310.bracu.assignment2.entities.Schedule;
import org.cse310.bracu.assignment2.repository.ConnectionPool;
import org.cse310.bracu.assignment2.service.CourseService;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws SQLException {
        startApplication();
    }

    private static void startApplication() throws SQLException {
        var dbConnections = ConnectionPool.getInstance();
        CourseService courseService = new CourseService(dbConnections.getConnection());
        var cse310CourseSection1 = Course.builder()
                .courseID(UUID.randomUUID().toString())
                .courseCode("CSE310")
                .availableSeat(35)
                .totalCapacity(35)
                .section(String.valueOf(1))
                .schedule(new ArrayList<>())
                .build();
        cse310CourseSection1.getSchedule().add(new Schedule(DayOfWeek.MONDAY,
                LocalTime.of(14, 0),
                LocalTime.of(15, 30),
                1));
        cse310CourseSection1.getSchedule().add(new Schedule(DayOfWeek.WEDNESDAY,
                LocalTime.of(14, 0),
                LocalTime.of(15, 30),
                1));
        courseService.addCourse(cse310CourseSection1);
        var cse310CourseSection2 = Course.builder()
                .courseID(UUID.randomUUID().toString())
                .courseCode("CSE310")
                .availableSeat(35)
                .totalCapacity(35)
                .section(String.valueOf(2))
                .schedule(new ArrayList<>())
                .build();
        cse310CourseSection2.getSchedule().add(new Schedule(DayOfWeek.MONDAY,
                LocalTime.of(14, 0),
                LocalTime.of(15, 30),
                1));
        cse310CourseSection2.getSchedule().add(new Schedule(DayOfWeek.WEDNESDAY,
                LocalTime.of(14, 0),
                LocalTime.of(15, 30),
                1));
        courseService.addCourse(cse310CourseSection2);
        courseService.findAllCourses()
                .forEach(System.out::println);
        dbConnections.closeAllConnections();
    }
}
