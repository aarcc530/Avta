package com.example.avta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.time.Duration;

public class SetEvent extends Event {
    LocalDateTime start;
    LocalDateTime end;

    public SetEvent (String name, int length, String subject, LocalDateTime start, LocalDateTime end) {
        super.name = name;
        this.start = start;
        this.end = end;
        super.subject = subject;
        super.length += (start.getHour() - end.getHour());
    }

}
