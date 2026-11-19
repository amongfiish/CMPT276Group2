package ca.accessarchitects.cmpt276group2;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// Main calendar activity enabling users to view medication routine data on a calendar.
public class CalendarActivity extends AppCompatActivity
{

    // called when the calendar activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendar = findViewById(R.id.calendar);
        TextView dateView = findViewById(R.id.date_view);

        // called when a new date is selected in the calendar view
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // update the TextView to match the date selected in the CalendarView
                String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateView.setText(date);
            }
        });
    }
}