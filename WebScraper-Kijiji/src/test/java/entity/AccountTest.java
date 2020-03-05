package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Dikshit Dikshit
 */
public class AccountTest {

    @Test
    public void testGetId() {
        Account instance = new Account();
        int expResult = 1;
        instance.setId(1);
        Integer result = instance.getId();
        assertEquals(expResult, result);

    }

    @Test
    public void testSetId() {
        Integer id = 15;
        Account instance = new Account();
        instance.setId(id);
        assertEquals(id, instance.getId());

    }

    @Test
    public void testGetDisplayName() {
        Account instance = new Account();
        String expResult = "hello";
        instance.setDisplayName("hello");
        String result = instance.getDisplayName();
        assertEquals(expResult, result);

    }

    @Test
    public void testSetDisplayName() {

        String displayName = "hello";
        Account instance = new Account();
        instance.setDisplayName(displayName);
        assertEquals(displayName, instance.getDisplayName());
    }

    @Test
    public void testGetUser() {

        Account instance = new Account();
        String expResult = "user";
        instance.setUser("user");
        String result = instance.getUser();
        assertEquals(expResult, result);

    }

    @Test
    public void testSetUser() {
        String user = " ";
        Account instance = new Account();
        instance.setUser(user);
        assertEquals(user, instance.getUser());

    }

    @Test
    public void testGetPassword() {

        Account instance = new Account();
        String expResult = "root";
        instance.setPassword("root");
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPassword() {

        String password = "root";
        Account instance = new Account();
        instance.setPassword(password);
        assertEquals(password, instance.getPassword());

    }

    @Test
    public void testHashCode() {

        Account instance = new Account();
        instance.setId(1);
        int expResult = 1;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        instance.setId(null);
        int expResult2 = 0;
        int result2 = instance.hashCode();
        assertEquals(expResult2, result2);

    }

    @Test
    public void testEquals() {

        Object object = new Account(1, "hello", "root", "user");
        Account instance = new Account(1, "hello", "root", "user");
        boolean expResult = true;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);

    }

    @Test
    public void testToString() {

        Account instance = new Account(1, "hello", "root", "user");
        String expResult = "entity.Account[ id=1 ]";
        String result = instance.toString();
        System.out.println(instance.toString());
        assertEquals(expResult, result);

    }

}
