package org.cse310.bracu.assignment2.entities;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    private String courseID;
    private String courseCode;
    private Integer totalCapacity;
    private Integer availableSeat;
    private List<Schedule> schedule;
    private Set<Student> students;
    private Set<Lecturer> lecturers;
    private Integer version;
}
