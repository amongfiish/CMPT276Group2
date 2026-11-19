package ca.accessarchitects.cmpt276group2;

import java.util.Vector;

public class DataHandler {
    public Vector<Contact> contacts;
    public Vector<Medication> medications;

    private static DataHandler singleton = null;

    public static DataHandler getInstance()
    {
        if (singleton == null)
        {
            singleton = new DataHandler();
        }

        return singleton;
    }

    public void loadSql()
    {
        // TODO: load from database
    }

    public void saveSql()
    {
        // TODO: save to database
    }

    public Contact getContact(int index)
    {
        if (index < 0 || index > contacts.size())
        {
            return null;
        }

        return contacts.elementAt(index);
    }

    public Medication getMedication(int index)
    {
        if (index < 0 || index > medications.size())
        {
            return null;
        }

        return medications.elementAt(index);
    }

    public int addContact(Contact contact)
    {
        contacts.add(contact);
        return contacts.size() - 1;
    }

    public int addMedication(Medication medication)
    {
        medications.add(medication);
        return medications.size() - 1;
    }

    public boolean removeContact(int index)
    {
        if (contacts.remove(index) == null)
        {
            return false;
        }
        return true;
    }

    public boolean removeMedication(int index)
    {
        if (medications.remove(index) == null)
        {
            return false;
        }

        return true;
    }

    private DataHandler()
    {
        contacts = new Vector<>();
        medications = new Vector<>();
    }
}
