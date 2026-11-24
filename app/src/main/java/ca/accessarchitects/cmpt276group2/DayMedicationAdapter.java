package ca.accessarchitects.cmpt276group2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Shows the medications for whichever calendar day the user tapped.
public class DayMedicationAdapter extends RecyclerView.Adapter<DayMedicationAdapter.ViewHolder>
{
    private final List<Medication> medications = new ArrayList<>();

    public void setMedications(List<Medication> newMedications)
    {
        medications.clear();
        medications.addAll(newMedications);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Medication med = medications.get(position);
        holder.name.setText(med.getName());
        holder.dosage.setText(med.getDosage());
    }

    @Override
    public int getItemCount()
    {
        return medications.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final TextView dosage;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.dayMedName);
            dosage = itemView.findViewById(R.id.dayMedDosage);
        }
    }
}