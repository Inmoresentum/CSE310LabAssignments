package org.cse310.bracu.assignment2.runner;

import org.cse310.bracu.assignment2.entities.Course;
import org.cse310.bracu.assignment2.entities.Schedule;
import org.cse310.bracu.assignment2.entities.Student;
import org.cse310.bracu.assignment2.entities.UserType;
import org.cse310.bracu.assignment2.repository.ConnectionPool;
import org.cse310.bracu.assignment2.security.Session;
import org.cse310.bracu.assignment2.service.CourseService;
import org.cse310.bracu.assignment2.service.LecturerService;
import org.cse310.bracu.assignment2.service.StudentService;

import java.io.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class ApplicationRunner {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out), true);
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StudentService studentService;
    private static LecturerService lecturerService;
    private static CourseService courseService;

    private ApplicationRunner() {
    }

    public static void run() throws SQLException {
        var dbConnections = ConnectionPool.getInstance();
        studentService = StudentService.getInstance();
        lecturerService = LecturerService.getInstance();
        courseService = CourseService.getInstance();
        var cse310Section1 = new Course(UUID.randomUUID().toString(),
                "CSE310",
                "1",
                11,
                11,
                new ArrayList<>(),
                new HashSet<>(),
                new HashSet<>(),
                1);
        var cse310Section2 = new Course(UUID.randomUUID().toString(),
                "CSE310",
                "2",
                9,
                9,
                new ArrayList<>(),
                new HashSet<>(),
                new HashSet<>(),
                1);
        var firstSchedule = new Schedule(DayOfWeek.SUNDAY, LocalTime.of(12, 30), LocalTime.of(13, 50), 1);
        var secondSchedule = new Schedule(DayOfWeek.TUESDAY, LocalTime.of(12, 30), LocalTime.of(13, 50), 1);
        var scheduleList = List.of(firstSchedule, secondSchedule);
        cse310Section1.setSchedule(scheduleList);
        cse310Section2.setSchedule(scheduleList);
        courseService.addCourse(cse310Section1);
        courseService.addCourse(cse310Section2);

        Student student = new Student(UUID.randomUUID().toString(), "whatever", "whatever@whatever.com", "admin", "20101396",new HashSet<>(), 1);
        studentService.register(student.getName(), student.getStudentID(), student.getEmail(), student.getEncryptedPassword());
        try {
            takeInputAndStartExecution();
            br.close();
            pw.close();
        } catch (IOException e) {
            pw.println("Failed to take input and start operations");
        }
        dbConnections.closeAllConnections();
    }

    private static void takeInputAndStartExecution() throws IOException {
        promptUser();
        while (true) {
            String curInput = readLineAndTokenize().nextToken();
            if (curInput.equals("1")) {
                handleAuthOperation();
            } else if (curInput.equals("2")) {
                handleRegistration();
            } else if (curInput.equals("3")) {
                handlePrintingAllCoursesAndSeatStatus();
            } else if (Session.isSession() &&
                    Session.getSession().getUserType().equals(UserType.LECTURER) &&
                    curInput.equals("4")) {
                handlePrintingRegisteredStudentByCourseAndSection();
            } else if (Session.isSession() &&
                    Session.getSession().getUserType().equals(UserType.LECTURER) &&
                    curInput.equals("5")) {
                handleLogoutOperation();
                break;
            } else if (curInput.equals("4")) {
                handleLogoutOperation();
                break;
            }
            promptUser();
        }
        pw.println("Bye");
    }

    @SuppressWarnings("DuplicatedCode")
    private static void handleAuthOperation() throws IOException {
        if (Session.isSession()) {
            Session.invalidateSession();
            pw.println("Log out Successful!!");
            return;
        }
        pw.println("Login as: ");
        pw.println("1. Student");
        pw.println("2. Lecturer");
        pw.println("3. Return");
        var tk = readLineAndTokenize();
        String curInput = tk.nextToken();
        switch (curInput) {
            case "1" -> {
                pw.println("Please enter your email: ");
                var email = readLineAndTokenize().nextToken();
                pw.println("Please enter your password: ");
                var password = readLineAndTokenize().nextToken();
                if (!studentService.authenticate(email, password)) {
                    pw.println("Please enter a valid email and password and try again.");
                    return;
                }
                pw.println("Welcome " + Session.getSession().getName());
                handleCourseRegistrationProcess();
            }
            case "2" -> {
                pw.println("Please enter your email: ");
                var email = readLineAndTokenize().nextToken();
                pw.println("Please enter your password: ");
                var password = readLineAndTokenize().nextToken();
                if (lecturerService.authenticate(email, password)) {
                    pw.println("Welcome " + Session.getSession().getName());
                    return;
                }
                pw.println("Please enter a valid email and password and try again.");
            }
            case "3" -> pw.println("Okay Going back");
            default -> pw.println("Please try again with a valid input.");
        }
    }

    private static void handleRegistration() throws IOException {
        if (Session.isSession()) {
            pw.println("You are already logged in as " + Session.getSession().getName());
            pw.println("If you need to register another account, please logout and try again.");
            return;
        }
        pw.println("Register as: ");
        pw.println("1. Student");
        pw.println("2. Lecturer");
        pw.println("3. Go Back");
        var curInput = readLineAndTokenize().nextToken();
        switch (curInput) {
            case "1" -> {
                pw.print("Please enter your name: ");
                pw.flush();
                var studentName = readLineAndTokenize().nextToken();
                pw.print("Please enter your id: ");
                pw.flush();
                var studentId = readLineAndTokenize().nextToken();
                pw.print("Please enter your email: ");
                pw.flush();
                var studentEmail = readLineAndTokenize().nextToken();
                pw.print("Please enter your password: ");
                pw.flush();
                var studentPassword = readLineAndTokenize().nextToken();
                if (!studentService.register(studentName, studentId, studentEmail, studentPassword)) {
                    pw.println("Student ID is or email already in use.");
                    pw.println("Please try again later.");
                    return;
                }
                pw.println("Registration successful.");
            }
            case "2" -> {
                pw.print("Please enter your name: ");
                pw.flush();
                var lecturerName = readLineAndTokenize().nextToken();
                var lecturerId = UUID.randomUUID().toString();
                pw.print("Please enter your email: ");
                pw.flush();
                var lecturerEmail = readLineAndTokenize().nextToken();
                pw.print("Please enter your password: ");
                pw.flush();
                var lecturerPassword = readLineAndTokenize().nextToken();
                if (lecturerService.register(lecturerName, lecturerEmail, lecturerPassword, lecturerId)) {
                    pw.println("Registration successfully");
                    return;
                }
                pw.println("Email already in use.");
                pw.println("Please try again later.");
            }
            case "3" -> pw.println("Going back.");
            default -> pw.println("Please enter a valid input and try again.");
        }
    }

    private static void handleCourseRegistrationProcess() {
        pw.println("You can register for the below courses ");
        var curStudent = (Student) Session.getSession();
        try {
            List<Course> offeredCourses = courseService.findAllCourses();
            for (int i = 0; i < offeredCourses.size(); i++) {
                var curCourse = offeredCourses.get(i);
                pw.print((i + 1) + " " + curCourse.getCourseCode() + " section " + curCourse.getSection() +
                        " Total Seat " + curCourse.getTotalCapacity() + " seat remaining" + curCourse.getAvailableSeat() + " ");
                for (Schedule schedule : curCourse.getSchedule()) {
                    pw.print(schedule + " ");
                }
                pw.println();
            }
            pw.print("Which course you would like to register for: ");
            pw.flush();
            var courseIndex = Integer.parseInt(readLineAndTokenize().nextToken());
            var courCourse = offeredCourses.get(courseIndex - 1);
            try {
                studentService.addCourseToStudent(curStudent.getUserId(), courCourse);
            } catch (SQLIntegrityConstraintViolationException e) {
                pw.println("You have already taken this course....");
            }
            pw.println("Done... The have been added");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlePrintingAllCoursesAndSeatStatus() {
        try {
            List<Course> offeredCourses = courseService.findAllCourses();
            for (int i = 0; i < offeredCourses.size(); i++) {
                var curCourse = offeredCourses.get(i);
                pw.print((i + 1) + " " + curCourse.getCourseCode() + " section " + curCourse.getSection() +
                        " Total Seat " + curCourse.getTotalCapacity() + " seat remaining" + curCourse.getAvailableSeat() + " ");
                for (Schedule schedule : curCourse.getSchedule()) {
                    pw.print(schedule + " ");
                }
                pw.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleLogoutOperation() {
        Session.invalidateSession();
    }

    private static void handlePrintingRegisteredStudentByCourseAndSection() {
        if (Session.isSession()
                && !Session.getSession().getUserType().equals(UserType.LECTURER)) {
            throw new IllegalStateException("Must need to be a" +
                    " Lecturer to perform this operations.");
        }
    }

    private static void promptUser() {
        pw.println("Please select one of the below operations: ");
        if (Session.isSession())
            pw.println("1. Logout.");
        else pw.println("1. Login.");
        pw.println("2. Register.");
        pw.println("3. See all available courses and their seat status.");
        if (Session.isSession() &&
                Session.getSession().getUserType().equals(UserType.LECTURER)) {
            pw.println("4. Get the List of Registered Students by Course and Sections.");
            pw.println("5. Exit.");
        } else
            pw.println("4. Exit.");
    }

    private static StringTokenizer readLineAndTokenize() throws IOException {
        return new StringTokenizer(br.readLine());
    }
}
