package com.example.avta;

import android.content.Intent;
import android.os.Bundle;

import com.example.avta.ui.MovableEventListFragment;
import com.example.avta.ui.SetEventListFragment;
import com.example.avta.ui.WeekViewFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SetEventListFragment.OnFragmentInteractionListener,
        MovableEventListFragment.OnFragmentInteractionListener,
        WeekViewFragment.OnFragmentInteractionListener {
    private AppBarConfiguration mAppBarConfiguration;

    private static final int ADD_SET_EVENT_ACTIVITY_REQUEST_CODE = 0;
    private static final int ADD_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE = 1;
    private static final int EDIT_SET_EVENT_ACTIVITY_REQUEST_CODE = 2;
    private static final int EDIT_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE = 3;
    private static final int SIGN_IN_ACTIVITY_REQUEST_CODE = 4;

    private ArrayList<Event> events;
    private SetEventListFragment setEventListFragment;
    private MovableEventListFragment movableEventListFragment;
    private WeekViewFragment weekViewFragment;
    private String userId = "";
    private boolean signedIn = false;

    private FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddSetEventActivity.class);
                intent.putParcelableArrayListExtra("events", events);
                startActivityForResult(intent, ADD_SET_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddMovableEventActivity.class);
                startActivityForResult(intent, ADD_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab3 = findViewById(R.id.signInButton);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                startActivityForResult(intent, SIGN_IN_ACTIVITY_REQUEST_CODE);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_set_event_list, R.id.nav_movable_event_list, R.id.nav_week_view)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        events = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void onSetEventListFragmentInitialize(SetEventListFragment fragment) {
        setEventListFragment = fragment;
    }

    public void onMovableEventListFragmentInitialize(MovableEventListFragment fragment) {
        movableEventListFragment = fragment;
    }

    public void onWeekViewFragmentInitialize(WeekViewFragment fragment) {
        weekViewFragment = fragment;
        weekViewFragment.updateEvents(events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("in onActivityResult");
        System.out.println(requestCode);
        System.out.println(resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SET_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            SetEvent e = data.getParcelableExtra("event");
            events.add(e);
            events = ScheduleAlgorithm.algorithm(events);
            if (movableEventListFragment != null)
                movableEventListFragment.notifyAdapter(events);
            if (setEventListFragment != null)
                setEventListFragment.notifyAdapter(events);
            if (weekViewFragment != null)
                weekViewFragment.notifyWeekView(events);
        }
        else if (requestCode == ADD_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MovableEvent e = data.getParcelableExtra("event");
            events.add(e);

            events = ScheduleAlgorithm.algorithm(events);
            if (signedIn)
                FirebasePushPull.fireBasePush(events, fb, userId);
            if (movableEventListFragment != null)
                movableEventListFragment.notifyAdapter(events);
            if (setEventListFragment != null)
                setEventListFragment.notifyAdapter(events);
            if (weekViewFragment != null)
                weekViewFragment.notifyWeekView(events);
        }
        else if (requestCode == SIGN_IN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            userId = data.getStringExtra("userId");
            if (fb.document("/users/" + userId) == null)
                fb.collection("/users").add(userId);
            signedIn = true;
        }
    }
}
