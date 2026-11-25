package ca.accessarchitects.cmpt276group2;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

// manages information about when a user needs to take their
// medication. works in tandem with Notification.
public class Routine
{
    public enum Weekday
    {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY,
        FRIDAY, SATURDAY, SUNDAY, COUNT
    }

    // parent medication
    public Medication medication;

    Map<Weekday, Boolean> weekdays = new EnumMap<>(Weekday.class);
    LocalTime time;

    public Routine(Medication medication)
    {
        this.medication = medication;
        this.time = LocalTime.MIDNIGHT;

        for (Weekday weekday : Weekday.values())
        {
            this.weekdays.put(weekday, false);
        }
    }

    public boolean getWeekday(Weekday weekday)
    {
        try
        {
            return weekdays.get(weekday);
        }
        catch (Exception e)
        {
            // TODO
            return false;
        }
    }

    public void setWeekday(Weekday weekday)
    {
        try
        {
            weekdays.replace(weekday, true);
        }
        catch (Exception e)
        {
            // TODO: some form of error handling
            return;
        }
    }

    public void unsetWeekday(Weekday weekday)
    {
        try
        {
            weekdays.replace(weekday, false);
        }
        catch (Exception e)
        {
            // TODO
            return;
        }
    }
}
