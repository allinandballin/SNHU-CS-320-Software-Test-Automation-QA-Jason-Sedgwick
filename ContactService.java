import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ContactService {

    private final Map<String, Contact> contacts = new HashMap<>();

    // Add a contact with a unique ID. Returns true if added, false if duplicate.
    public boolean addContact(Contact contact) {
        if (contact == null)
            throw new IllegalArgumentException("contact cannot be null");
        String id = contact.getContactId();
        if (id == null)
            throw new IllegalArgumentException("contactId cannot be null");
        if (contacts.containsKey(id))
            return false; // enforce uniqueness
        contacts.put(id, contact);
        return true;
    }

    // Delete a contact by ID. Returns true if something was removed.
    public boolean deleteContact(String contactId) {
        if (contactId == null)
            throw new IllegalArgumentException("contactId cannot be null");
        return contacts.remove(contactId) != null;
    }

    // --- Update operations (by ID) ---
    public boolean updateFirstName(String contactId, String firstName) {
        Contact c = getExisting(contactId);
        c.setFirstName(firstName); // Contact’s own validation applies
        return true;
    }

    public boolean updateLastName(String contactId, String lastName) {
        Contact c = getExisting(contactId);
        c.setLastName(lastName);
        return true;
    }

    public boolean updatePhone(String contactId, String phone) {
        Contact c = getExisting(contactId);
        c.setPhone(phone);
        return true;
    }

    public boolean updateAddress(String contactId, String address) {
        Contact c = getExisting(contactId);
        c.setAddress(address);
        return true;
    }

    // Optional helper for tests/inspection
    public Contact getById(String contactId) {
        return contacts.get(contactId);
    }

    // Internal helper to fetch or throw if missing
    private Contact getExisting(String contactId) {
        if (contactId == null)
            throw new IllegalArgumentException("contactId cannot be null");
        Contact c = contacts.get(contactId);
        if (c == null)
            throw new NoSuchElementException("No contact with id=" + contactId);
        return c;
    }
}
