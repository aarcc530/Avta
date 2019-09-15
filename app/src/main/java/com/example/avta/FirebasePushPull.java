package com.example.avta;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirebasePushPull {
    public static void fireBasePush (ArrayList<Event> events, FirebaseFirestore db, String userId) {
        ArrayList<String[]> strings = new ArrayList<>();
        CollectionReference cr = db.collection("/users/"+ userId +"/data/");
        for (Event e : events) {
            strings.add(FireBaseConversion.fireBaseConversion(e)[events.indexOf(e)]);
        }


        for (String[] s : strings) {
            cr.add(s[0]);
                cr.document("/users/"+ userId +"/data/" + s[0]).set(s[1]);
            }

    }

    public static ArrayList<Event> fireBasePull (FirebaseFirestore db, String userId) {
        ArrayList<Event> events= new ArrayList<Event>();
        Event event = new Event();
        CollectionReference cr = db.collection("/users/"+ userId +"/data/");
        events.addAll(cr.get().getResult().toObjects(Event.class));
        return events;
    }

}
