package com.example.avta;

import net.fortuna.ical4j.*;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import android.app.Activity;
import android.content.Intent;
import 	android.content.Context;
import android.os.Environment;
import android.view.View;

import java.io.*;
import java.time.Duration;
import java.util.Calendar;


public class CreateCalendar extends Activity {


    void makeCalendar (Event[] events) {
        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();

        for (int i = 0; i < events.length; i++) {

            //Sets start time for event
            java.util.Calendar start = java.util.Calendar.getInstance();
            start.set(java.util.Calendar.MONTH, events[i].getStart().getMonthValue());
            start.set(java.util.Calendar.DAY_OF_MONTH, events[i].getStart().getDayOfMonth());
            start.set(java.util.Calendar.YEAR, events[i].getStart().getYear());
            start.set(java.util.Calendar.HOUR_OF_DAY, events[i].getStart().getHour());
            start.set(java.util.Calendar.MINUTE, events[i].getStart().getMinute());

            //Sets end time for event
            java.util.Calendar end = java.util.Calendar.getInstance();
            end.set(java.util.Calendar.MONTH, events[i].getEnd().getMonthValue());
            end.set(java.util.Calendar.DAY_OF_MONTH, events[i].getEnd().getDayOfMonth());
            end.set(java.util.Calendar.YEAR, events[i].getEnd().getYear());
            end.set(java.util.Calendar.HOUR_OF_DAY, events[i].getEnd().getHour());
            end.set(java.util.Calendar.MINUTE, events[i].getEnd().getMinute());

            //Creates the event and UID
            VEvent tempEvent = new VEvent(new net.fortuna.ical4j.model.Date(start.getTime()), new net.fortuna.ical4j.model.Date(end.getTime()), events[i].getEventName());
            RandomUidGenerator uidGenerator = new RandomUidGenerator();
            tempEvent.getProperties().add(uidGenerator.generateUid());

            calendar.getComponents().add(tempEvent);
        }
        File file = null;
        File root = Environment.getDataDirectory();
        FileOutputStream out;
        if (root.canWrite()){
           File dir = new File(root.getAbsolutePath() + "/Calendars");
           dir.mkdirs();
           file = new File(dir, "calendar.ics");
           try {
               out = new FileOutputStream(file);
               try {
                   CalendarOutputter outputter = new CalendarOutputter();
                   outputter.setValidating(false);
                   outputter.output(calendar, out);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   out.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }

        }




    }
}
