package com.example.avta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddSetEventActivity extends AppCompatActivity {
    private DateTimeFormatter dateFormat, timeFormat;
    private EditText startDateInput, startTimeInput, endDateInput, endTimeInput;
    private LocalDateTime startDate, endDate, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_event);

        startTimeInput = findViewById(R.id.startTimeInput);
        startDateInput = findViewById(R.id.startDateInput);
        endTimeInput = findViewById(R.id.endTimeInput);
        endDateInput = findViewById(R.id.endDateInput);

        // Set date/time formatters
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        dateFormat = DateTimeFormatter.ofPattern("EEE, d MMM YYYY", locale);
        timeFormat = DateTimeFormatter.ofPattern("hh:mm a", locale);

        // Set start and end date
        currentDate = LocalDateTime.now().withNano(0).withSecond(0);
        startDate = currentDate;
        endDate = startDate.plusMinutes(30);

        // Change title text
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Set Event");
        }

        updateDateTimeDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_set_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete_button) {
            Intent intent = new Intent();
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            setResult(RESULT_OK, intent);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateDateTimeDisplay() {
        startDateInput.setText(dateFormat.format(startDate));
        startTimeInput.setText(timeFormat.format(startDate));
        endDateInput.setText(dateFormat.format(endDate));
        endTimeInput.setText(timeFormat.format(endDate));
    }

    public void selectStartDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate = startDate.withYear(year)
                                .withMonth(month + 1)
                                .withDayOfMonth(dayOfMonth);

                        if (startDate.isAfter(endDate)) {
                            endDate = startDate;
                        }

                        updateDateTimeDisplay();
                    }
                }, startDate.getYear(),
                startDate.getMonthValue() - 1,
                startDate.getDayOfMonth());

        datePickerDialog.getDatePicker()
                .setMinDate(currentDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        datePickerDialog.show();
    }

    public void selectStartTime(View view) {
        new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startDate = startDate.withHour(hourOfDay).withMinute(minute);
                updateDateTimeDisplay();
            }
        }, startDate.getHour(), startDate.getMinute(), false).show();
    }

    public void selectEndDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate = endDate.withYear(year)
                                .withMonth(month + 1)
                                .withDayOfMonth(dayOfMonth);

                        updateDateTimeDisplay();
                    }
                }, endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());

        datePickerDialog.getDatePicker()
                .setMinDate(startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        datePickerDialog.show();
    }

    public void selectEndTime(View view) {
        new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endDate = endDate.withHour(hourOfDay).withMinute(minute);
                updateDateTimeDisplay();
            }
        }, endDate.getHour(), endDate.getMinute(), false).show();
    }
}
