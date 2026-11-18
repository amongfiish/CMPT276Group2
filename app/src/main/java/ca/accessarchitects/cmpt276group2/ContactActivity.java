package ca.accessarchitects.cmpt276group2;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private final List<Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_contacts);

        emptyText = findViewById(R.id.emptyText);
        RecyclerView recyclerView = findViewById(R.id.contactsRecyclerView);
        FloatingActionButton addButton = findViewById(R.id.addContactButton);

        adapter = new ContactAdapter(contacts, position -> showContactDialog(position));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> showContactDialog(-1));
        updateEmptyState();
    }


    private void showContactDialog(int position) {
        boolean isEdit = position >= 0;
        Contact existing = isEdit ? contacts.get(position) : null;

        View form = LayoutInflater.from(this).inflate(R.layout.add_contact, null);
        TextInputEditText inputName = form.findViewById(R.id.inputName);
        TextInputEditText inputEmail = form.findViewById(R.id.inputEmail);
        TextInputEditText inputPhone = form.findViewById(R.id.inputPhone);

        if (isEdit) {
            inputName.setText(existing.getName());
            inputEmail.setText(existing.getEmail());
            inputPhone.setText(existing.getPhoneNumber());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Edit Contact" : "Add Contact")
                .setView(form)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null);

        if (isEdit) {
            builder.setNeutralButton("Delete", (d, which) -> {
                contacts.remove(position);
                adapter.notifyItemRemoved(position);
                updateEmptyState();
            });
        }

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String name = inputName.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String phone = inputPhone.getText().toString().trim();

                    if (TextUtils.isEmpty(name)) {
                        inputName.setError("Name is required");
                        return;
                    }
                    if (TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {
                        Toast.makeText(this, "Enter an email or phone number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (isEdit) {
                        existing.setName(name);
                        existing.setEmail(email);
                        existing.setPhoneNumber(phone);
                        adapter.notifyItemChanged(position);
                    } else {
                        contacts.add(new Contact(name, email, phone));
                        adapter.notifyItemInserted(contacts.size() - 1);
                    }
                    updateEmptyState();
                    dialog.dismiss();
                }));

        dialog.show();
    }

    private void updateEmptyState() {
        emptyText.setVisibility(contacts.isEmpty() ? View.VISIBLE : View.GONE);
    }
}