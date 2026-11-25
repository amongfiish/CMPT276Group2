package ca.accessarchitects.cmpt276group2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// Days with medication are highlighted, and tapping one lists what to take that day.
public class CalendarActivity extends AppCompatActivity
{
    private static final String HINT = "Tap a highlighted day to see its medications";

    private TextView monthTitle;
    private TextView dayTitle;
    private RecyclerView calendarGrid;
    private DayMedicationAdapter dayAdapter;

    // the month that is currently on screen
    private int shownYear;
    private int shownMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        monthTitle = findViewById(R.id.monthTitle);
        dayTitle = findViewById(R.id .dayTitle);
        calendarGrid = findViewById(R.id.calendarGrid);
        ImageButton prevButton = findViewById(R.id.prevMonthButton);
        ImageButton nextButton = findViewById(R.id.nextMonthButton);
        RecyclerView dayList = findViewById(R.id.dayMedicationsRecyclerView);

        calendarGrid.setLayoutManager(new GridLayoutManager(this, 7));

        dayAdapter = new DayMedicationAdapter();
        dayList.setLayoutManager(new LinearLayoutManager(this));
        dayList.setAdapter(dayAdapter);

        // start on the current month
        Calendar today = Calendar.getInstance();
        shownYear = today.get(Calendar.YEAR);
        shownMonth = today.get(Calendar.MONTH);

        prevButton.setOnClickListener(v -> changeMonth(-1));
        nextButton.setOnClickListener(v -> changeMonth(1));

        showMonth();
    }

    // refresh in case medications were added or removed while we were on another screen
    @Override
    protected void onResume()
    {
        super.onResume();
        showMonth();
    }

    private void changeMonth(int amount)
    {
        shownMonth += amount;
        if (shownMonth < 0) {
            shownMonth = 11;
            shownYear--;
        } else if (shownMonth > 11) {
            shownMonth = 0;
            shownYear++;
        }
        showMonth();
    }

    // builds the grid for the month we're on and highlights the medication days
    private void showMonth()
    {
        monthTitle.setText(monthName(shownMonth) + " " + shownYear);

        // the old selection doesn't apply to a different month, so clear it
        dayTitle.setText(HINT);
        dayAdapter.setMedications(new ArrayList<>());

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(shownYear, shownMonth, 1);

        int blanksBeforeFirst = cal.get(Calendar.DAY_OF_WEEK) - 1; // week starts on Sunday
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Integer> days = new ArrayList<>();
        for (int i = 0; i < blanksBeforeFirst; i++) {
            days.add(0);
        }

        Set<Integer> medicationDays = new HashSet<>();
        for (int day = 1; day <= daysInMonth; day++) {
            days.add(day);
            if (!getMedicationsFor(shownYear, shownMonth, day).isEmpty()) {
                medicationDays.add(day);
            }
        }

        calendarGrid.setAdapter(new CalendarDayAdapter(days, medicationDays, this::showDayDetails));
    }

    // fills the list under the calendar with the tapped day's medications
    private void showDayDetails(int day)
    {
        dayTitle.setText(monthName(shownMonth) + " " + day + ", " + shownYear);
        dayAdapter.setMedications(getMedicationsFor(shownYear, shownMonth, day));
    }

    // every medication that needs to be taken on the given date
    private List<Medication> getMedicationsFor(int year, int month, int day)
    {
        List<Medication> result = new ArrayList<>();
        DataHandler data = DataHandler.getInstance();

        for (int i = 0; i < data.getMedicationCount(); i++) {
            Medication med = data.getMedication(i);
            if (isTakenOn(med, year, month, day)) {
                result.add(med);
            }
        }
        return result;
    }

    // TODO: Medication doesn't have routines yet, so for now everything is highlighted
    private boolean isTakenOn(Medication med, int year, int month, int day)
    {
        return true;
    }

    private String monthName(int month)
    {
        return new DateFormatSymbols().getMonths()[month];
    }
}