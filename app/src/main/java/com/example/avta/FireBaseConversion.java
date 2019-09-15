package com.example.avta;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;




public class FireBaseConversion {
    /* Available Data Types for Firebase Database:
     * String
     * Number
     * bolean
     * timestamp
     * array (of others)
    */

    public static String[][] fireBaseConversion (Event event) {
        ArrayList<String[]> arrayListStrings = new ArrayList<String[]>();
        arrayListStrings.add(new String[] {"name", event.getEventName()});
        arrayListStrings.add(new String[] {"length", String.valueOf(event.getLength())});
        arrayListStrings.add(new String[] {"subject", event.getSubject()});
        arrayListStrings.add(new String[] {"start", event.getStart().toString()});
        arrayListStrings.add(new String[] {"end", event.getEnd().toString()});
        if (event instanceof  MovableEvent) {
            arrayListStrings.add(new String[] {"enjoyLevel", String.valueOf(((MovableEvent) event).getEnjoyLevel())});
            arrayListStrings.add(new String[] {"dueDate", ((MovableEvent) event).getDueDate().toString()});
            arrayListStrings.add(new String[] {"eventType", "MovableEvent"});
        }
        if (event instanceof SetEvent)
            arrayListStrings.add(new String[] {"eventType", "SetEvent"});
        String[][] eventStrings = new String[arrayListStrings.size()][2];
        eventStrings = arrayListStrings.toArray(eventStrings);
        return eventStrings;
    }

    public static Event firebaseReversion (String[][] eventStrings) {
        ArrayList<String[]> eventStringsList = new ArrayList<String[]>(Arrays.asList(eventStrings));
        String name = null;
        String length = null;
        String subject = null;
        String start = null;
        String end = null;
        String enjoyLevel = null;
        String dueDate = null;
        String eventType = null;
        for (int i = 0; i < eventStringsList.size(); i++)
        {
            if (eventStringsList.get(i)[0].equals("eventType")) {
                eventType = eventStringsList.get(i)[1];
                eventStringsList.remove(i);
                break;
            }
        }
        if (eventType.equals("SetEvent")) {
            for (String[] arr : eventStringsList){
                switch (arr[0]){
                    case "name":
                        name = arr[1];
                        continue;
                    case "length":
                        length = arr[1];
                        continue;
                    case "subject":
                        subject = arr[1];
                        continue;
                    case "start":
                        start = arr[1];
                        continue;
                    case "end":
                        end = arr[1];
                        continue;
                    default:
                        continue;
                }
            }
            return new SetEvent(name, subject, LocalDateTime.parse(start), LocalDateTime.parse(end));

        }
        if (eventType.equals("MovableEvent")) {
            for (String[] arr : eventStringsList) {
                switch (arr[0]) {
                    case "name":
                        name = arr[1];
                        continue;
                    case "length":
                        length = arr[1];
                        continue;
                    case "subject":
                        subject = arr[1];
                        continue;
                    case "start":
                        start = arr[1];
                        continue;
                    case "end":
                        end = arr[1];
                        continue;
                    case "enjoyLevel":
                        enjoyLevel = arr[1];
                        continue;
                    case "dueDate":
                        dueDate = arr[1];
                    default:
                        continue;
                }
            }
            MovableEvent movableEvent = new MovableEvent(name, Integer.valueOf(length), subject, LocalDateTime.parse(dueDate), Integer.valueOf(enjoyLevel));
            movableEvent.setStartEnd(LocalDateTime.parse(start), LocalDateTime.parse(end));
            return movableEvent;
        }


        return null;
    }



}

