import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class ContactServiceTest {

    @Test
    void addContact_enforcesUniqueIds() {
        ContactService svc = new ContactService();
        Contact a = new Contact("ID1", "A", "B", "1234567890", "addr");
        assertTrue(svc.addContact(a));

        // Same ID should be rejected
        Contact dup = new Contact("ID1", "X", "Y", "0000000000", "addr2");
        assertFalse(svc.addContact(dup));
    }

    @Test
    void deleteContact_byId() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("ID2", "A", "B", "1234567890", "addr"));

        assertTrue(svc.deleteContact("ID2"));
        // Deleting again should return false
        assertFalse(svc.deleteContact("ID2"));
    }

    @Test
    void updateFields_byId_success() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("X", "A", "B", "1234567890", "addr"));

        assertTrue(svc.updateFirstName("X", "NewA"));
        assertTrue(svc.updateLastName("X", "NewB"));
        assertTrue(svc.updatePhone("X", "0000000000"));
        assertTrue(svc.updateAddress("X", "New Address"));

        Contact updated = svc.getById("X");
        assertEquals("NewA", updated.getFirstName());
        assertEquals("NewB", updated.getLastName());
        assertEquals("0000000000", updated.getPhone());
        assertEquals("New Address", updated.getAddress());
    }

    @Test
    void update_nonexistentId_throws() {
        ContactService svc = new ContactService();
        assertThrows(NoSuchElementException.class, () -> svc.updateFirstName("NOPE", "A"));
        assertThrows(NoSuchElementException.class, () -> svc.updateLastName("NOPE", "B"));
        assertThrows(NoSuchElementException.class, () -> svc.updatePhone("NOPE", "1234567890"));
        assertThrows(NoSuchElementException.class, () -> svc.updateAddress("NOPE", "addr"));
    }

    @Test
    void update_invalidValues_propagateValidation() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("Z", "A", "B", "1234567890", "addr"));

        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("Z", "12345")); // not 10 digits
        assertThrows(IllegalArgumentException.class, () -> svc.updateFirstName("Z", "ABCDEFGHIJK")); // >10 chars
        assertThrows(IllegalArgumentException.class, () -> svc.updateAddress("Z", null)); // null
    }

    @Test
    void addOrDelete_nullId_orNullContact_throw() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.addContact(null));
        assertThrows(IllegalArgumentException.class, () -> svc.deleteContact(null));
    }
}
