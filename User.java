import java.util.Scanner;
import java.util.ArrayList;
/**
 * Team 1 Project
 * Creates a User with username and password.
 * 
 *
 * @author Malhar Bhide
 * @version October 30, 2025
 */
public class User implements UserInterface {
    private String username;
    private String password;
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<Reservation> reservations = new ArrayList<>(); // store all reservations

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
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static void addUser(User user) {
        userList.add(user);
    }
    public static void removeUser(User user) {
        userList.remove(user);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Seating seating = new Seating(); // shared seating layout to make new reservations
        System.out.println("Welcome to the Reservation System!");
        System.out.println("Please select an option:");
        System.out.println("1. Create a User");
        System.out.println("2. Remove a User");
        System.out.println("3. Login");
        System.out.println("4. Create a Reservation");
        System.out.println("5. Cancel a Reservation");
        String option = sc.nextLine();
        switch(option) {
            case "1":
                String username = "";
                System.out.println("Enter a Username:");
                while (true) {
                    boolean foundDuplicate = false;
                    username = sc.nextLine();
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(userToRemove) && userList.get(i).getPassword().equals(passToRemove)) {
                            userList.remove(i);
                            System.out.println("User removed successfully!");
                            userRemoved = true;
                            break;
                        }
                    }
                    if (!userRemoved) {
                        System.out.println("User not found or incorrect password.");
                    }
                    break;
                case "3":
                    // login(sc);
                    System.out.println("Enter your Username:");
                    String loginUsername = sc.nextLine();
                    System.out.println("Enter your Password:");
                    String loginPassword = sc.nextLine();
                    boolean loginSuccess = false;
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(loginUsername) && userList.get(i).getPassword().equals(loginPassword)) {
                            System.out.println("Login successful! Welcome, " + loginUsername + "!");
                            loginSuccess = true;  
                            break;
                        }
                    }
                }
                // removeUser(sc);
                break;
            case "3":
                // login(sc);
                break;
            case "4":
                // createReservation(sc);
                System.out.println("Enter a reservation time")
              
                System.out.println("How many people are in your party? Please enter an integer.");
                size = scanner.nextInt(); 
                System.out.println("What day would you like to have your reservation?");
                dayf = scanner.nextLine(); 
                System.out.println("What time would you like to reserve?")
                timef = scanner.nextLine(); 
                Reservation br = new 
                




                break;
            case "5":
                //cancelReservation
        }
    }
}