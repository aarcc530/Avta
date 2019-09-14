package com.example.avta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;

import java.util.Date;
import java.util.List;

public class AddSetEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_event);
    }

    public void selectStartDatetime(View view) {
        new DoubleDateAndTimePickerDialog.Builder(view.getContext())
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)
                .title("Set start and end time")
                .tab0Text("Start")
                .tab1Text("End")
                .listener(new DoubleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(List<Date> dates) {
                        for (Date d : dates) {
                            System.out.println(d);
                        }
                    }
                }).display();
    }
}
