package ca.accessarchitects.cmpt276group2;

import java.util.Vector;

// the outer User class
public class User {
    // information that will be linked to their user account when we implement that
    private String name;
    private String email;
    private String phoneNumber;

    private Vector<Medication> medications;
    private Vector<Contact> contacts;

    // IDE generated getters/setters.
    public void addMedication(Medication medication)
    {
        if (medications.contains(medication))
        {
            return;
        }
        medications.add(medication);
    }

    public void removeMedication(Medication medication)
    {
        if (!medications.contains(medication))
        {
            return;
        }
        medications.remove(medication);
    }

    public void addContact(Contact contact)
    {
        if (contacts.contains(contact))
        {
            return;
        }
        contacts.add(contact);
    }

    public void removeContact(Contact contact)
    {
        if (!contacts.contains(contact))
        {
            return;
        }
        contacts.remove(contact);
    }
}
