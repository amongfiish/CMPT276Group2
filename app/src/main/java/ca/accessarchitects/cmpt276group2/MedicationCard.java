package ca.accessarchitects.cmpt276group2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

// a custom View type that displays information about a medication to the user
public class MedicationCard extends LinearLayout {
    public MedicationCard(Context context, Medication medication) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.medication_card, this);
        ((TextView)findViewById(R.id.medicationName)).setText(medication.getName());
    }

    @Override
    protected void onFinishInflate() {
        // never gets called because we're manually calling inflate() in the constructor
        super.onFinishInflate();
    }
}
