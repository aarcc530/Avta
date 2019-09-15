package com.example.avta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SetEventAdapter extends RecyclerView.Adapter<SetEventAdapter.EventViewHolder> {
    class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHeading;
        TextView textViewDescription;
        TextView textViewTime;

        EventViewHolder(View itemView) {
            super(itemView);

            textViewHeading = itemView.findViewById(R.id.textViewHeading);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

    private ArrayList<Event> events;
    private Context context;

    public SetEventAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        if (events.get(position) instanceof MovableEvent) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        Event listItem = events.get(position);

        holder.itemView.setVisibility(View.VISIBLE);
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        holder.textViewHeading.setText(listItem.getEventName());
        holder.textViewDescription.setText(listItem.getSubject());

        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("hh:mm a E, MMM dd yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

        if (listItem.getStart().toLocalDate().equals(listItem.getEnd().toLocalDate())) {
            holder.textViewTime.setText(
                    context.getApplicationContext().getString(
                            R.string.event_list_time_same_day,
                            listItem.getStart().format(timeFormatter),
                            listItem.getEnd().format(timeFormatter),
                            listItem.getStart().format(dateFormatter))
            );
        }
        else {
            holder.textViewTime.setText(
                    context.getApplicationContext().getString(
                            R.string.event_list_time_different_day,
                            listItem.getStart().format(fullFormatter),
                            listItem.getEnd().format(fullFormatter)).replace("\\n", "\n")
            );
        }
    }

    public void changeEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
