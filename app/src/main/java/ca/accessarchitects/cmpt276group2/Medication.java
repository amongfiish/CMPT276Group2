package ca.accessarchitects.cmpt276group2;

import java.util.Vector;

// stores information about a medication
public class Medication {
    final private String name;
    final private String dosage;
    // TODO image of medication

    private Vector<Routine> routines;

    public Medication(String name, String dosage)
    {
        this.name = name;
        this.dosage = dosage;
    }

    public String getName()
    {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    // TODO: addRoutine() and related for release 2 :)
}
