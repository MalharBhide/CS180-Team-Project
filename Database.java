import java.util.ArrayList;
import java.io.*;
/**
 * Team 1 Project
 * Database class that implements DatabaseInterface to manage users and reservations.
 *
 * @author Malhar Bhide
 * @version Nov 24th, 2025
 */
public class Database implements DatabaseInterface {
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
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
    public static ArrayList<User> getUserList() {
        return userList;
    }
    public static ArrayList<Reservation> getReservations() {
        return reservations;
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
}