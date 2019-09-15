package com.example.avta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class AddMovableEventActivity extends AppCompatActivity {
    private static SeekBar seekBar;
    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movable_event);
        enterApproxTime();
    }

    public void enterApproxTime(View view) {
        new PickerDialogFragment().show(getFragmentManager(), "dialog");
    }

    public void enterApproxTime() {
        seekBar = (SeekBar) findViewById(R.id.seek_Bar);
        textView = (TextView) findViewById(R.id.editText3);

        textView.setText("Enjoyment : " + seekBar.getProgress() + " / " +seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textView.setText("Enjoyment : " + progress + " / " + seekBar.getMax());
                        //Toast.makeText(AddMovableEventActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(AddMovableEventActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText("Enjoyment : " + progress_value + " / " + seekBar.getMax());
                        //Toast.makeText(AddMovableEventActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
