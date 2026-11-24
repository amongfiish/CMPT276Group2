package ca.accessarchitects.cmpt276group2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

// Draws the grid of days for one month. A day number of 0 means an empty cell
// (the blank spots before the 1st of the month).
public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder> {

    public interface OnDayClickListener {
        void onDayClick(int dayOfMonth);
    }

    private final List<Integer> days;
    private final Set<Integer> medicationDays;
    private final OnDayClickListener listener;

    public CalendarDayAdapter(List<Integer> days, Set<Integer> medicationDays,
                              OnDayClickListener listener) {
        this.days = days;
        this.medicationDays = medicationDays;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        int day = days.get(position);

        // empty cell before the 1st of the month
        if (day == 0) {
            holder.dayNumber.setText("");
            holder.dayNumber.setBackground(null);
            holder.dayNumber.setOnClickListener(null);
            holder.dayNumber.setClickable(false);
            return;
        }

        holder.dayNumber.setText(String.valueOf(day));

        if (medicationDays.contains(day)) {
            holder.dayNumber.setBackgroundResource(R.drawable.day_highlight);
            holder.dayNumber.setTextColor(0xFFFFFFFF);
            holder.dayNumber.setContentDescription("Day " + day + ", has medication");
            holder.dayNumber.setOnClickListener(v -> listener.onDayClick(day));
        } else {
            holder.dayNumber.setBackground(null);
            holder.dayNumber.setTextColor(0xFF222222);
            holder.dayNumber.setContentDescription("Day " + day);
            holder.dayNumber.setOnClickListener(null);
            holder.dayNumber.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        final TextView dayNumber;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayNumber = (TextView) itemView;
        }
    }
}