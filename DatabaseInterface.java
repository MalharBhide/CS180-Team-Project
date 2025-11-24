import java.util.ArrayList;
import java.io.*;
/**
 * Team 1 Project
 * DatabaseInterface to define methods for managing users and reservations.
 *
 * @author Malhar Bhide
 * @version Nov 24th, 2025
 */
public interface DatabaseInterface {
    public static void addUser(User user);
    public static void removeUser(User user);
    public static ArrayList<User> getUserList();
    public static ArrayList<Reservation> getReservations();
    public static void addReservation(Reservation reservation);
    public static void removeReservation(Reservation reservation);
    public static void saveUsersToFile();
    public static void loadUsers();
    public static void saveReservationsToFile();
    public static void loadReservations();
}