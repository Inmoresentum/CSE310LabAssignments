package org.cse310.bracu.assignment2.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer version;

    public Schedule(DayOfWeek day, LocalTime startTime, LocalTime endTime, Integer version) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.version = version;
    }

    public static ScheduleBuilder builder() {
        return new ScheduleBuilder();
    }

    @Override
    public String toString() {
        final var formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return "Day: " + day +
                ", startTime: " + startTime.format(formatter) +
                ", endTime: " + endTime.format(formatter);
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static class ScheduleBuilder {
        private DayOfWeek day;
        private LocalTime startTime;
        private LocalTime endTime;
        private Integer version;

        ScheduleBuilder() {
        }

        public ScheduleBuilder day(DayOfWeek day) {
            this.day = day;
            return this;
        }

        public ScheduleBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public ScheduleBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public ScheduleBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public Schedule build() {
            return new Schedule(this.day, this.startTime, this.endTime, this.version);
        }

        public String toString() {
            return "Schedule.ScheduleBuilder(day=" + this.day + ", startTime=" +
                    this.startTime + ", endTime=" + this.endTime +
                    ", version=" + this.version + ")";
        }
    }
}
