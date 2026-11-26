package ca.accessarchitects.cmpt276group2;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;

// functionally the same as ContactActivity. might be able to condense these into one file.
// see that file for comments.
public class MedicationActivity extends AppCompatActivity
{

    private MedicationAdapter adapter;
    private TextView emptyText;
    private LocalTime medicationTime = LocalTime.MIDNIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_medications);

        emptyText = findViewById(R.id.emptyText);
        RecyclerView recyclerView = findViewById(R.id.medicationsRecyclerView);
        FloatingActionButton addButton = findViewById(R.id.addMedicationButton);

        adapter = new MedicationAdapter(this::showMedicationDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> showMedicationDialog(-1));

        updateEmptyState();
    }

    private void showMedicationDialog(int position)
    {
        boolean isEdit = position >= 0;
        Medication existing = DataHandler.getInstance().getMedication(position);

        medicationTime = LocalTime.MIDNIGHT;

        View form = LayoutInflater.from(this).inflate(R.layout.add_medication, null);
        TextInputEditText inputName = form.findViewById(R.id.inputName);
        TextInputEditText inputDosage = form.findViewById(R.id.inputDosage);
        TextView inputTime = form.findViewById(R.id.timeText);

        Button setTimeButton = form.findViewById(R.id.buttonSetTime);
        setTimeButton.setOnClickListener(v -> {
            // TODO: make the spinner reflect the previously set time.
            new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
        });

        if (isEdit)
        {
            medicationTime = existing.getTime();

            inputName.setText(existing.getName());
            inputDosage.setText(existing.getDosage());
        }
        // TODO: make inputTime text update when the time selector exits.
        inputTime.setText(medicationTime.toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Edit Medication" : "Add Medication")
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null);

        if (isEdit)
        {
            builder.setNeutralButton("Delete", (d, which) -> {
                DataHandler.getInstance().removeMedication(position);
                adapter.notifyItemRemoved(position);
                updateEmptyState();
            });
        }

        AlertDialog dialogCreateMedication = builder.create();

        dialogCreateMedication.setOnShowListener(d ->
                dialogCreateMedication.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = inputName.getText().toString().trim();
                    String dosage = inputDosage.getText().toString().trim();

                    if (TextUtils.isEmpty(name)) {
                        inputName.setError("Name is required");
                        return;
                    }

                    if (isEdit) {
                        existing.setName(name);
                        existing.setDosage(dosage);
                        existing.setTime(medicationTime);
                        adapter.notifyItemChanged(position);
                    } else {
                        int newPosition = DataHandler.getInstance().addMedication(new Medication(name, dosage));
                        DataHandler.getInstance().getMedication(newPosition).setTime(medicationTime);
                        adapter.notifyItemInserted(newPosition);
                    }
                    updateEmptyState();
                    dialogCreateMedication.dismiss();
                }));

        dialogCreateMedication.show();
    }

    public void processSelectedTime(int hour, int minute)
    {
        medicationTime = LocalTime.of(hour, minute);
    }

    private void updateEmptyState()
    {
        emptyText.setVisibility(DataHandler.getInstance().getMedicationCount() == 0 ? View.VISIBLE : View.GONE);
    }
}