import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
 * CS 180 Team Project
 * UserTest
 *
 *  This test class checks that the User class behaves as expected.
 * It tests things like creating users, changing usernames/passwords,
 * and adding or removing users from the shared user list.
 *
 * @author Jiyara Bhatia
 */

class UserTest {

    private User user1;
    private User user2;

    /**
     * This method runs before each test
     * It creates two sample User objects and clears the shared user list
     * so that each test starts with a clean state
     */

    @BeforeEach
    void setUp() {
        // clear out the private static "userList" field using reflection
        // (we need reflection because userList is private)
        try {
            Field field = User.class.getDeclaredField("userList");
            field.setAccessible(true); // allows access to the private field

            ((ArrayList<User>) field.get(null)).clear(); // clear the shared list

        } catch (Exception e) {

            e.printStackTrace();
        }


        user1 = new User("johnDoe", "12345");
        user2 = new User("janeDoe", "abcde");
    }

    /**
     * This runs after each test and makes sure userList is empty again
     * It’s basically cleanup so one test doesn’t affect the next
     */

    @AfterEach
    void clear() {

        try {

            Field field = User.class.getDeclaredField("userList");
            field.setAccessible(true);

            ((ArrayList<User>) field.get(null)).clear();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Tests that the User constructor and getter methods work correctly.
     * It verifies that the username and password are stored properly.
     */

    @Test
    void testConstructorAndGetters() {
        assertEquals("johnDoe", user1.getUsername());
        assertEquals("12345", user1.getPassword());
    }

    /**
     * Tests that the setter methods correctly update a User’s info.
     */
    @Test
    void testSetters() {
        user1.setUsername("carol");
        user1.setPassword("xyz123");

        assertEquals("carol", user1.getUsername());
        assertEquals("xyz123", user1.getPassword());
    }

    /**
     * Tests that when we add users using the addUser() method,
     * they actually get added to the static userList inside the class.
     */

    @Test
    void testAddUser() throws Exception {
        User.addUser(user1);
        User.addUser(user2);

        // access the private static userList again to confirm the changes
        Field field = User.class.getDeclaredField("userList");
        field.setAccessible(true);
        ArrayList<User> userList = (ArrayList<User>) field.get(null);

        assertTrue(userList.contains(user1), "User list should contain user1");
        assertTrue(userList.contains(user2), "User list should contain user2");
        assertEquals(2, userList.size(), "User list should have exactly 2 users");
    }

    /**
     * Tests that removeUser() actually deletes a user from the userList.
     */

    @Test
    void testRemoveUser() throws Exception {

        User.addUser(user1);
        User.addUser(user2);


        User.removeUser(user1);


        Field field = User.class.getDeclaredField("userList");
        field.setAccessible(true);
        ArrayList<User> userList = (ArrayList<User>) field.get(null);

        assertFalse(userList.contains(user1), "User1 should have been removed");
        assertTrue(userList.contains(user2), "User2 should still be in the list");
        assertEquals(1, userList.size(), "User list should have only one user now");
    }

    /**
     * this makes sure that every test starts
     * with an empty userList so tests don’t interfere with each other.
     */

    @Test
    void testUserListBetweenTests () throws Exception {
        Field field = User.class.getDeclaredField("userList");
        field.setAccessible(true);
        ArrayList<User> userList = (ArrayList<User>) field.get(null);

        assertTrue(userList.isEmpty(), "userList should start empty for each test");
    }
}

