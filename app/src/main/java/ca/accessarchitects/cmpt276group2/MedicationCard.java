package ca.accessarchitects.cmpt276group2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MedicationCard extends LinearLayout {
    public MedicationCard(Context context, Medication medication) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.medication_card, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
