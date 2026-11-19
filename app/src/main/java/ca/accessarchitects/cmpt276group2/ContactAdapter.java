package ca.accessarchitects.cmpt276group2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnContactClickListener {
        void onContactClick(int position);
    }

    private final OnContactClickListener listener;

    public ContactAdapter(OnContactClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = DataHandler.getInstance().getContact(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.phone.setText(contact.getPhoneNumber());

        holder.edit.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onContactClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataHandler.getInstance().getContactCount();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView email;
        final TextView phone;
        final ImageButton edit;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.medicationName);
            email = itemView.findViewById(R.id.medicationDosage);
            phone = itemView.findViewById(R.id.contactPhone);
            edit = itemView.findViewById(R.id.editMedicationButton);
        }
    }
}