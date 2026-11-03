// Last edited by Lucas (November 2nd, 2026)

package ca.accessarchitects.cmpt276group2;

import java.util.Vector;

public class User {
    private String name;
    private String email;
    private String phoneNumber;

    private Vector<Medication> medications;
    private Vector<Contact> contacts;

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
