import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

/**
 * Team 1 Project
 * Creates a User with username and password.
 *
 * @author Malhar Bhide
 * @author Himangi Nepal
 * 
 * @version October 30, 2025
 */
public class User implements UserInterface, Serializable {
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
        saveUsersToFile(); 
    }

    public static void removeUser(User user) {
        userList.remove(user); 
        saveUsersToFile(); 
    }

    public static void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))){
            oos.writeObject(userList); 
            System.out.println("User saved successfully"); 
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage()); 
        }
    }

    public static void loadUsers() {
        File file = new File("users.dat");
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            userList = (ArrayList<User>) ois.readObject();
            System.out.println("Users loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public static void saveReservationsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    public static void loadReservations() { // load method so that data persists
        File file = new File("reservations.dat");
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            reservations = (ArrayList<Reservation>) ois.readObject(); //read obj and cast it to arraylist
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Seating seating = new Seating(); // shared seating layout to make new reservations

        // load saved data before menu starts
        loadUsers(); 
        loadReservations();

        System.out.println("Welcome to the Reservation System!");

        String option = ""; // declare before while loop

        while (!option.equals("6")) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Create a User");
            System.out.println("2. Remove a User");
            System.out.println("3. Login");
            System.out.println("4. Create a Reservation");
            System.out.println("5. Cancel a Reservation");
            System.out.println("6. Exit");
            option = sc.nextLine();

            switch (option) {
                case "1":
                    String username = "";
                    System.out.println("Enter a Username:");
                    while (true) {
                        boolean foundDuplicate = false;
                        username = sc.nextLine();
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).getUsername().equals(username)) {
                                System.out.println("Username already exists. Please try again.");
                                foundDuplicate = true;
                                break;
                            }
                        }
                        if (!foundDuplicate) break;
                    }

                    System.out.println("Enter a Password:");
                    String password = sc.nextLine();
                    User newUser = new User(username, password);
                    addUser(newUser);
                    System.out.println("User created successfully!");
                    break;

                case "2":
                    boolean userRemoved = false;
                    System.out.println("Enter the Username of the User to remove:");
                    String userToRemove = sc.nextLine();
                    System.out.println("Enter the Password of the User to remove:");
                    String passToRemove = sc.nextLine();

                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(userToRemove)
                                && userList.get(i).getPassword().equals(passToRemove)) {
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
                    System.out.println("Enter your Username:");
                    String loginUsername = sc.nextLine();
                    System.out.println("Enter your Password:");
                    String loginPassword = sc.nextLine();
                    boolean loginSuccess = false;

                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(loginUsername)
                                && userList.get(i).getPassword().equals(loginPassword)) {
                            System.out.println("Login successful! Welcome, " + loginUsername + "!");
                            loginSuccess = true;
                            break;
                        }
                    }

                    if (!loginSuccess) {
                        System.out.println("Login failed. Incorrect username or password.");
                    }
                    break;

                case "4":
                    System.out.println("Enter a reservation time (e.g. 6:00pm):");
                    String time = sc.nextLine();
                    System.out.println("What day would you like to have your reservation?");
                    String day = sc.nextLine();
                    System.out.println("How many people are in your party? Please enter an integer:");
                    int partySize = sc.nextInt();
                    sc.nextLine(); // clear buffer

                    Reservation newReservation = new Reservation(time, day, partySize, seating);
                    newReservation.bookReservation();
                    reservations.add(newReservation);
                    saveReservationsToFile();
                    System.out.println("Reservation created successfully!");
                    break;

                case "5":
                    System.out.println("Enter reservation day:");
                    String cancelDay = sc.nextLine();
                    System.out.println("Enter reservation time:");
                    String cancelTime = sc.nextLine();

                    boolean found = false;
                    for (int i = 0; i < reservations.size(); i++) {
                        Reservation r = reservations.get(i);
                        if (r.getDay().equalsIgnoreCase(cancelDay)
                                && r.getTime().equalsIgnoreCase(cancelTime)) {
                            r.cancelReservation();
                            reservations.remove(i);
                            saveReservationsToFile();
                            System.out.println("Reservation canceled successfully!");
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("No reservation found for " + cancelDay + " at " + cancelTime + ".");
                    }
                    break;

                case "6":
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        sc.close();
    }
}
