// Last edited by Lucas (November 4th, 2026)

package ca.accessarchitects.cmpt276group2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ((Button)findViewById(R.id.buttonNewMedication)).setOnClickListener(v -> {
            // new medication view (Dan)
        });

        ((Button)findViewById(R.id.buttonNewContact)).setOnClickListener(v -> {
            // new contact view (Aneesh)
        });

        Medication medication = new Medication("TestMedication", "1mg");

        // test with some dummy 'MedicationCard' objects
        for (int i = 0; i < 10; ++i)
        {
            MedicationCard card = new MedicationCard(this, medication);
            ((LinearLayout)findViewById(R.id.cardParent)).addView(card);
        }
    }
}