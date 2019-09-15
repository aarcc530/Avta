package com.example.avta.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthChangeListener;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewDisplayable;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.avta.Event;
import com.example.avta.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeekViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ArrayList<Event> events;
    private WeekView<Event> weekView;

    public WeekViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        weekView = view.findViewById(R.id.weekView);
        weekView.setMonthChangeListener(new MonthChangeListener<Event>() {
            @Override
            public List<WeekViewDisplayable<Event>> onMonthChange(Calendar startDate, Calendar endDate) {
                LocalDateTime monthStart = LocalDateTime.ofInstant(startDate.toInstant(),
                        startDate.getTimeZone().toZoneId());
                LocalDateTime monthEnd = LocalDateTime.ofInstant(endDate.toInstant(),
                        endDate.getTimeZone().toZoneId());

                List<WeekViewDisplayable<Event>> displayables = new ArrayList<>();

                for (Event e : events) {
                    if (e.getEnd().isAfter(monthStart) && e.getStart().isBefore(monthEnd)) {
                        displayables.add(e);
                    }
                }

                return displayables;
            }
        });

        weekView.goToCurrentTime();
    }

    public void notifyWeekView() {
        System.out.println("ouhoohiiohiooi");

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mListener.onWeekViewFragmentInitialize(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onWeekViewFragmentInitialize(WeekViewFragment fragment);
    }
}
