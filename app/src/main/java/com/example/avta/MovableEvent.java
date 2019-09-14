package com.example.avta;

import java.time.LocalDateTime;

public class MovableEvent extends Event {
    LocalDateTime dueDate;
    int enjoyLevel;


    public MovableEvent (String name, int length, String subject, LocalDateTime dueDate, int enjoyLevel) {
        this.name = name;
        this.length = length;
        this.subject = subject;
        this.dueDate = dueDate;
        this.enjoyLevel = enjoyLevel;
    }


}
