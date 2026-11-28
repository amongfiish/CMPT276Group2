package ca.accessarchitects.cmpt276group2;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.time.LocalDate;

// stores information about a medication
public class Medication
{
    // routine replacement
    public enum Weekday
    {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY,
        FRIDAY, SATURDAY, SUNDAY, COUNT
    }

    Map<Weekday, Boolean> weekdays = new EnumMap<>(Weekday.class);
    LocalTime time;

    // misc data
    private String name;
    private String dosage;

    private String lastTakenDate = "";

    // TODO image of medication

    public Medication(String name, String dosage)
    {
        this.name = name;
        this.dosage = dosage;
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

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    // getters/setters
    public String getName()
    {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public boolean isTakenToday()
    {
        return lastTakenDate.equals(LocalDate.now().toString());
    }

    public void markTakenToday()
    {
        lastTakenDate = LocalDate.now().toString();
    }

    public void markNotTaken()
    {
        lastTakenDate = "";
    }

    // TODO: addRoutine() and related for release 2 :)
}
