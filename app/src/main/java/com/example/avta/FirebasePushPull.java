package com.example.avta;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirebasePushPull {
    public static void fireBasePush (ArrayList<Event> events, FirebaseFirestore db, String userId) {
        CollectionReference cr = db.collection("/users/"+ userId +"/data/");
        cr.add(events);
    }

    public static ArrayList<Event> fireBasePull (FirebaseFirestore db, String userId) {
        ArrayList<Event> events= new ArrayList<Event>();
        Event event = new Event();
        CollectionReference cr = db.collection("/users/"+ userId +"/data/");
        events.addAll(cr.get().getResult().toObjects(Event.class));
        return events;
    }

}
