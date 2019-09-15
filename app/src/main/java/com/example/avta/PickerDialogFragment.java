package com.example.avta;

import android.content.Context;

import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;
import mobi.upod.timedurationpicker.TimeDurationPicker;

public class PickerDialogFragment extends TimeDurationPickerDialogFragment {
    TimeDurationPickerCallback callback;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);

        if (ctx instanceof TimeDurationPickerCallback) {
            callback = (TimeDurationPickerCallback) ctx;
        }
    }

    @Override
    protected long getInitialDuration() {
        return 15 * 60 * 1000;
    }

    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.HH_MM;
    }

    @Override
    public void onDurationSet(TimeDurationPicker view, long duration) {
        callback.updateDuration(duration);
    }
}
