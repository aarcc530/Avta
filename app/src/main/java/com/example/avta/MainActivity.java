package com.example.avta;

import android.content.Intent;
import android.os.Bundle;

import com.example.avta.ui.MovableEventListFragment;
import com.example.avta.ui.SetEventListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SetEventListFragment.OnFragmentInteractionListener,
        MovableEventListFragment.OnFragmentInteractionListener {
    private AppBarConfiguration mAppBarConfiguration;

    private static final int ADD_SET_EVENT_ACTIVITY_REQUEST_CODE = 0;
    private static final int ADD_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE = 1;

    private ArrayList<Event> events;
    private SetEventListFragment setEventListFragment;
    private MovableEventListFragment movableEventListFragment;

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_set_event_list, R.id.nav_movable_event_list,
                R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
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

    public void onSetEventListFragmentInitialize(SetEventListFragment fragment) {
        setEventListFragment = fragment;
    }

    public void onMovableEventListFragmentInitialize(MovableEventListFragment fragment) {
        movableEventListFragment = fragment;
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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SET_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            SetEvent e = data.getParcelableExtra("event");
            events.add(e);

            if (setEventListFragment != null)
                setEventListFragment.notifyAdapter();
        }
        else if (requestCode == ADD_MOVABLE_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MovableEvent e = data.getParcelableExtra("event");
            events.add(e);

            if (movableEventListFragment != null)
                movableEventListFragment.notifyAdapter();
        }
    }
}
