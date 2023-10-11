package org.cse310.bracu.assignment2.entities;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Schedule {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer version;

    @Override
    public String toString() {
        final var formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return "Schedule{" +
                "day=" + day +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + endTime.format(formatter) +
                ", version=" + version +
                '}';
    }
}
