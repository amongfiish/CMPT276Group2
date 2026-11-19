package ca.accessarchitects.cmpt276group2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

// functionally identical to ContactAdapter. check there for relevant comments.
public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>
{
    private OnMedicationClickListener listener;

    public interface OnMedicationClickListener
    {
        void onMedicationClick(int position);
    }

    public MedicationAdapter(OnMedicationClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position)
    {
        Medication medication = DataHandler.getInstance().getMedication(position);
        holder.nameView.setText(medication.getName());
        holder.dosageView.setText(medication.getDosage());
        // TODO: holder.imageView.setImageBitmap(..);

        holder.editView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onMedicationClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataHandler.getInstance().getMedicationCount();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder
    {
        final TextView nameView;
        final TextView dosageView;
        // TODO: final ImageView imageView;
        final ImageButton editView;

        MedicationViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameView = itemView.findViewById(R.id.medicationName);
            dosageView = itemView.findViewById(R.id.medicationDosage);
            // TODO: imageView = itemView.findViewById(R.id.medicationImage);
            editView = itemView.findViewById(R.id.editMedicationButton);
        }
    }
}
