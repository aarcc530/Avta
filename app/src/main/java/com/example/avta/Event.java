package com.example.avta;


import java.time.LocalDateTime;

public class Event {
    String name;
    int length;
    String subject;
    LocalDateTime start;
    LocalDateTime end;

    public String getEventName () {
        return this.name;
    }
    public int getLength () {
        return this.length;
    }
    public String getSubject () {
        return this.subject;
    }
    public LocalDateTime getStart () {
        return this.start;
    }
    public LocalDateTime getEnd () {
        return this.end;
    }


}
