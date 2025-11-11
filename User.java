import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
/**
 * Team 1 Project
 * Creates a User with username and password.
 *
 * @author Malhar Bhide
 * @author Himangi Nepal
 * @version Nov 10th, 2025
 */
public class User implements UserInterface, Serializable {
    private String username;
    private String password;
    // shared resources
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<Reservation> reservations = new ArrayList<Reservation>();

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

    // thread-safe user methods
    public static void addUser(User user) {
        synchronized (userList) {
            userList.add(user);
            saveUsersToFile();
        }
    }

    public static void removeUser(User user) {
        synchronized (userList) {
            userList.remove(user);
            saveUsersToFile();
        }
    }

    // thread-safe reservation methods
    public static void addReservation(Reservation reservation) {
        synchronized (reservations) {
            reservations.add(reservation);
            saveReservationsToFile();
        }
    }

    public static void removeReservation(Reservation reservation) {
        synchronized (reservations) {
            reservations.remove(reservation);
            saveReservationsToFile();
        }
    }


    public static void saveUsersToFile() {
        synchronized (userList) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
                oos.writeObject(userList);
                System.out.println("User saved successfully");
            } catch (IOException e) {
                System.out.println("Error saving users: " + e.getMessage());
            }
        }
    }

    public static synchronized void loadUsers() {
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
        synchronized (reservations) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
                oos.writeObject(reservations);
            } catch (IOException e) {
                System.out.println("Error saving reservations: " + e.getMessage());
            }
        }
    }

    public static synchronized void loadReservations() {
        File file = new File("reservations.dat");
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            reservations = (ArrayList<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Seating seating = new Seating();

        loadUsers();
        loadReservations();

        System.out.println("Welcome to the Reservation System!");
        String option = "";
        boolean loginSuccess = false;

        while (!option.equals("6")) {
            System.out.println("Please select an option:");
            System.out.println("1. Create a User");
            System.out.println("2. Remove a User");
            System.out.println("3. Login");
            System.out.println("4. Create a Reservation");
            System.out.println("5. Cancel a Reservation");
            System.out.println("6. Exit");
            option = sc.nextLine();

            switch (option) {
                case "1": 
                    System.out.println("Enter a Username:");
                    String username = sc.nextLine();
                    while (true) {
                        boolean duplicate = false;
                        synchronized (userList) {
                            for (User u : userList) {
                                if (u.getUsername().equals(username)) {
                                    duplicate = true;
                                    break;
                                }
                            }
                        }
                        if (!duplicate) break;
                        System.out.println("Username already exists. Try again:");
                        username = sc.nextLine();
                    }

                    System.out.println("Enter a Password:");
                    String password = sc.nextLine();
                    User newUser = new User(username, password);
                    addUser(newUser);
                    System.out.println("User created successfully!");
                    break;

                case "2":
                    System.out.println("Enter Username to remove:");
                    String userToRemove = sc.nextLine();
                    System.out.println("Enter Password:");
                    String passToRemove = sc.nextLine();
                    boolean removed = false;

                    synchronized (userList) {
                        for (int i = 0; i < userList.size(); i++) {
                            User u = userList.get(i);
                            if (u.getUsername().equals(userToRemove) && u.getPassword().equals(passToRemove)) {
                                userList.remove(i);
                                saveUsersToFile();
                                removed = true;
                                break;
                            }
                        }
                    }

                    if (removed) System.out.println("User removed successfully!");
                    else System.out.println("User not found or incorrect password.");
                    break;

                case "3":
                    System.out.println("Enter your Username:");
                    String loginUsername = sc.nextLine();
                    System.out.println("Enter your Password:");
                    String loginPassword = sc.nextLine();
                    loginSuccess = false;

                    synchronized (userList) {
                        for (User u : userList) {
                            if (u.getUsername().equals(loginUsername) && u.getPassword().equals(loginPassword)) {
                                loginSuccess = true;
                                break;
                            }
                        }
                    }

                    if (loginSuccess) System.out.println("Login successful! Welcome, " + loginUsername + "!");
                    else System.out.println("Login failed. Incorrect username or password.");
                    break;

                case "4":
                    if (loginSuccess) {
                        System.out.println("Enter reservation time (e.g., 6:00pm) Our hours are from 9:00am-"
                                           + "9:00pm:");
                        String time = sc.nextLine();
                        System.out.println("Enter day:");
                        String day = sc.nextLine();
                        int partySize = 0;
                        while (true) {
                            System.out.println("Enter party size:");
                            try {
                                partySize = Integer.parseInt(sc.nextLine());
                                if (partySize <= 0) continue;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Try again.");
                            }
                        }
                        Reservation newReservation = new Reservation(time, day, partySize, seating);
                        newReservation.bookReservation();
                        addReservation(newReservation); // use synchronized method
                        System.out.println("Reservation created successfully!");
                        seating.displaySeats(day, time);
                        break;
                    } else {
                        System.out.println("User is not logged in. Please log in and try again.");
                        break;
                    }

                case "5": 
                    if (loginSuccess) {
                        System.out.println("Enter reservation day:");
                        String cancelDay = sc.nextLine();
                        System.out.println("Enter reservation time:");
                        String cancelTime = sc.nextLine();
                        boolean found = false;

                        synchronized (reservations) {
                            for (int i = 0; i < reservations.size(); i++) {
                                Reservation r = reservations.get(i);
                                if (r.getDay().equalsIgnoreCase(cancelDay) && 
                                    r.getTime().equalsIgnoreCase(cancelTime)) {
                                    r.cancelReservation();
                                    reservations.remove(i);
                                    saveReservationsToFile();
                                    found = true;
                                    break;
                                }
                            }
                        }
                    
                        if (found) System.out.println("Reservation canceled successfully!");
                        else System.out.println("No reservation found for that time/day.");
                        break;
                    } else {
                        System.out.println("User is not logged in. Please log in and try again.");
                        break;
                    }

                case "6":
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        sc.close();
    }
}