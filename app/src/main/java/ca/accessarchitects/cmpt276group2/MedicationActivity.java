package ca.accessarchitects.cmpt276group2;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;

// TODO: remove all the if statements sorry guys i'll fix this in a future push i just
//       wanted to get it working for now...

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

        ToggleButton toggleMonday = form.findViewById(R.id.toggleMonday);
        ToggleButton toggleTuesday = form.findViewById(R.id.toggleTuesday);
        ToggleButton toggleWednesday = form.findViewById(R.id.toggleWednesday);
        ToggleButton toggleThursday = form.findViewById(R.id.toggleThursday);
        ToggleButton toggleFriday = form.findViewById(R.id.toggleFriday);
        ToggleButton toggleSaturday = form.findViewById(R.id.toggleSaturday);
        ToggleButton toggleSunday = form.findViewById(R.id.toggleSunday);

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

            toggleMonday.setChecked(existing.getWeekday(Medication.Weekday.MONDAY));
            toggleTuesday.setChecked(existing.getWeekday(Medication.Weekday.TUESDAY));
            toggleWednesday.setChecked(existing.getWeekday(Medication.Weekday.WEDNESDAY));
            toggleThursday.setChecked(existing.getWeekday(Medication.Weekday.THURSDAY));
            toggleFriday.setChecked(existing.getWeekday(Medication.Weekday.FRIDAY));
            toggleSaturday.setChecked(existing.getWeekday(Medication.Weekday.SATURDAY));
            toggleSunday.setChecked(existing.getWeekday(Medication.Weekday.SUNDAY));
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

                        if (toggleMonday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.MONDAY);
                        }
                        if (toggleTuesday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.TUESDAY);
                        }
                        if (toggleWednesday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.WEDNESDAY);
                        }
                        if (toggleThursday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.THURSDAY);
                        }
                        if (toggleFriday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.FRIDAY);
                        }
                        if (toggleSaturday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.SATURDAY);
                        }
                        if (toggleSunday.isChecked())
                        {
                            existing.setWeekday(Medication.Weekday.SUNDAY);
                        }
                        adapter.notifyItemChanged(position);
                    } else {
                        int newPosition = DataHandler.getInstance().addMedication(new Medication(name, dosage));
                        DataHandler.getInstance().getMedication(newPosition).setTime(medicationTime);
                        if (toggleMonday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.MONDAY);
                        }
                        if (toggleTuesday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.TUESDAY);
                        }
                        if (toggleWednesday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.WEDNESDAY);
                        }
                        if (toggleThursday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.THURSDAY);
                        }
                        if (toggleFriday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.FRIDAY);
                        }
                        if (toggleSaturday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.SATURDAY);
                        }
                        if (toggleSunday.isChecked())
                        {
                            DataHandler.getInstance().getMedication(newPosition).setWeekday(Medication.Weekday.SUNDAY);
                        }
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