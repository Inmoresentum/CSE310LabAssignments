CREATE TABLE User
(
    userId            VARCHAR(255) PRIMARY KEY,
    name              VARCHAR(255)        NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    encryptedPassword VARCHAR(255)        NOT NULL,
    userType          ENUM('STUDENT', 'LECTURER') NOT NULL
);

CREATE TABLE Student
(
    userId    VARCHAR(255) PRIMARY KEY,
    studentId VARCHAR(255) UNIQUE NOT NULL,
    version   INT,
    FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Lecturer
(
    userId     VARCHAR(255) PRIMARY KEY,
    lecturerId VARCHAR(255) UNIQUE NOT NULL,
    FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Course
(
    courseID      VARCHAR(255) PRIMARY KEY,
    courseCode    VARCHAR(255) UNIQUE NOT NULL,
    totalCapacity INT                 NOT NULL,
    currentNumber INT                 NOT NULL,
    version       INT
);

CREATE TABLE Student_Course
(
    studentId VARCHAR(255),
    courseId  VARCHAR(255),
    FOREIGN KEY (studentId) REFERENCES Student (userId),
    FOREIGN KEY (courseId) REFERENCES Course (courseID),
    PRIMARY KEY (studentId, courseId)
);

CREATE TABLE Lecturer_Course
(
    lecturerId VARCHAR(255),
    courseId   VARCHAR(255),
    FOREIGN KEY (lecturerId) REFERENCES Lecturer (userId),
    FOREIGN KEY (courseId) REFERENCES Course (courseID),
    PRIMARY KEY (lecturerId, courseId)
);

CREATE TABLE Schedule
(
    scheduleID INT AUTO_INCREMENT PRIMARY KEY,
    day        ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    startTime  TIME NOT NULL,
    endTime    TIME NOT NULL,
    version    INT,
    courseId   VARCHAR(255),
    FOREIGN KEY (courseId) REFERENCES Course (courseID)
);
