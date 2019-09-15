package com.example.avta;

import net.fortuna.ical4j.model.*;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Event implements Parcelable {
    private String name;
    private long length;
    private String subject;
    private LocalDateTime start;
    private LocalDateTime end;

    String getEventName() {
        return this.name;
    }
    long getLength() {
        return this.length;
    }
    String getSubject() {
        return this.subject;
    }
    LocalDateTime getStart() {
        return this.start;
    }
    LocalDateTime getEnd() {
        return this.end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    Event(String name, long length, String subject, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.length = length;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }

    protected Event(Parcel in) {
        name = in.readString();
        length = in.readLong();
        subject = in.readString();
        start = LocalDateTime.parse(in.readString());
        end = LocalDateTime.parse(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(length);
        dest.writeString(subject);
        dest.writeString(start.toString());
        dest.writeString(end.toString());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
