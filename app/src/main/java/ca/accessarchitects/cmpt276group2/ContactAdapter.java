package ca.accessarchitects.cmpt276group2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// manages the elements in our scrolling list of contacts.
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
{

    public interface OnContactClickListener
    {
        void onContactClick(int position);
    }

    private final OnContactClickListener listener;

    public ContactAdapter(OnContactClickListener listener)
    {
        this.listener = listener;
    }

    // inflate list element layout
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    // populate list element
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position)
    {
        Contact contact = DataHandler.getInstance().getContact(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.phone.setText(contact.getPhoneNumber());

        holder.edit.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION)
            {
                listener.onContactClick(pos);
            }
        });
    }

    // how many contacts total?
    @Override
    public int getItemCount() {
        return DataHandler.getInstance().getContactCount();
    }

    // the data holder itself
    static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final TextView email;
        final TextView phone;
        final ImageButton edit;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactName);
            email = itemView.findViewById(R.id.contactEmail);
            phone = itemView.findViewById(R.id.contactPhone);
            edit = itemView.findViewById(R.id.editContactButton);
        }
    }
}