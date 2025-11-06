public interface UserInterface {
    public String getUsername();
    public String getPassword();
    public void setUsername(String username);
    public void setPassword(String password);
    public static void addUser(User user);
    public static void removeUser(User user);
}