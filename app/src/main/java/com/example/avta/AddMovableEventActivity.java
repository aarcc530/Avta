package com.example.avta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;


public class AddMovableEventActivity extends AppCompatActivity implements TimeDurationPickerCallback {
    private SeekBar seekBar;
    private TextView textView;
    private EditText durationInput;
    private long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movable_event);

        // Set up event duration selector
        durationInput = findViewById(R.id.durationInput);
        updateDuration(15*60*1000);

        // Set up seek bar for enjoyment value
        seekBar = findViewById(R.id.seek_Bar);
        textView = findViewById(R.id.enjoymentValue);

        textView.setText(getApplicationContext().getString(R.string.enjoyment_selector_text,
                0, seekBar.getMax()));
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textView.setText(getApplicationContext().getString(
                                R.string.enjoyment_selector_text,
                                progress, seekBar.getMax()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                }
        );

        // Change title text
        ActionBar actionBar = getSupportActionBar();
        System.out.println(actionBar == null);
        if (actionBar != null) {
            actionBar.setTitle("Add Movable Event");
        }
    }

    public void updateDuration(long duration) {
        this.duration = duration;
        long minutes = (duration / 1000) / 60;
        long hours = minutes / 60;
        minutes %= 60;
        durationInput.setText(getApplicationContext().getString(R.string.select_duration, hours,
                minutes));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_movable_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete_button) {
            // Hide keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            String name = ((EditText) findViewById(R.id.nameInput)).getText().toString().trim();
            // Check that event name is not empty
            if (name.length() == 0) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Event name can't be empty", Snackbar.LENGTH_LONG);
                snackbar.show();
                return super.onOptionsItemSelected(item);
            }

            // Check that duration is not 0
            if (duration == 0) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Duration can't be 0", Snackbar.LENGTH_LONG);
                snackbar.show();
                return super.onOptionsItemSelected(item);
            }

            MovableEvent e = new MovableEvent(name, duration / 1000 / 60,
                    ((EditText) findViewById(R.id.subjectInput)).getText().toString().trim(),
                    LocalDateTime.MAX, seekBar.getProgress());

            Intent intent = new Intent();
            intent.putExtra("event", e);
            setResult(RESULT_OK, intent);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void enterApproxTime(View view) {
        new PickerDialogFragment().show(getFragmentManager(), "dialog");
    }
}
