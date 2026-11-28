package ca.accessarchitects.cmpt276group2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

// Main screen list: every medication with a Taken button.
public class TakeMedicationAdapter extends RecyclerView.Adapter<TakeMedicationAdapter.ViewHolder>
{
    private static final int PURPLE = 0xFF6750A4;      // purple
    private static final int TAKEN_CARD = 0xFFE8E8E8;  // gray
    private static final int TAKEN_TEXT = 0xFF595959;  // dark gray
    private static final int NORMAL_DOSAGE = 0xFF444444;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication_due, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Medication med = DataHandler.getInstance().getMedication(position);
        if (med == null) {
            return;
        }

        holder.name.setText(med.getName());
        holder.dosage.setText(med.getDosage() + " · " + med.getTime());

        style(holder, med);

        holder.takenButton.setOnClickListener(v -> {
            // tap once to mark as taken, tap again to unmark
            if (med.isTakenToday()) {
                med.markNotTaken();
            } else {
                med.markTakenToday();
            }

            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                notifyItemChanged(pos);
            }

            // TODO (reminders): stopping remainder after taken

            // TODO (database): save medication
        });
    }

    private void style(ViewHolder holder, Medication med)
    {
        MaterialButton button = holder.takenButton;

        if (med.isTakenToday()) {
            holder.card.setCardBackgroundColor(TAKEN_CARD);
            holder.name.setTextColor(TAKEN_TEXT);
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.dosage.setTextColor(TAKEN_TEXT);

            button.setText("✓ Taken");
            button.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            button.setStrokeColor(ColorStateList.valueOf(TAKEN_TEXT));
            button.setTextColor(TAKEN_TEXT);
            button.setContentDescription(med.getName() + " taken. Tap to mark as not taken.");
        } else {
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.name.setTextColor(PURPLE);
            holder.name.setPaintFlags(holder.name.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.dosage.setTextColor(NORMAL_DOSAGE);

            button.setText("Mark taken");
            button.setBackgroundTintList(ColorStateList.valueOf(PURPLE));
            button.setStrokeColor(ColorStateList.valueOf(PURPLE));
            button.setTextColor(Color.WHITE);
            button.setContentDescription("Mark " + med.getName() + " as taken.");
        }
    }

    @Override
    public int getItemCount()
    {
        return DataHandler.getInstance().getMedicationCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        final MaterialCardView card;
        final TextView name;
        final TextView dosage;
        final MaterialButton takenButton;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            card = itemView.findViewById(R.id.dueCard);
            name = itemView.findViewById(R.id.dueName);
            dosage = itemView.findViewById(R.id.dueDosage);
            takenButton = itemView.findViewById(R.id.takenButton);
        }
    }
}