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
    void addUser(User user);
    void removeUser(User user);
    ArrayList<User> getUserList();
    ArrayList<Reservation> getReservations();
    void addReservation(Reservation reservation);
    void removeReservation(Reservation reservation);
    void saveUsersToFile();
    void loadUsers();
    void saveReservationsToFile();
    void loadReservations();
}