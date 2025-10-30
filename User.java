/**
 * Team 1 Project
 * Creates a User with username and password.
 * 
 *
 * @author Malhar Bhide
 * @version October 30, 2025
 */
public class User {
    private String username;
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void deleteUser() {
        this.username = null;
        this.password = null;
    }
}