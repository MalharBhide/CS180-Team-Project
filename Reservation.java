import java.io.*;

/**
 * CS 180 Team Project
 *
 * @author Himangi Nepal
 * @version Nov 10th, 2025
 */
public class Reservation implements ReservationInterface, Serializable {

    private String time; 
    private String day;
    private int partySize;
    private transient Seating seating; // transient so it’s not serialized
    private boolean isBooked;

    private String username;

    public Reservation(String time, String day, int partySize, Seating seating) {
        this.time = time;
        this.day = day;
        this.partySize = partySize;
        this.seating = seating;
        this.isBooked = false;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void viewOpenSeats() {
        seating.displaySeats(day, time);
    }

  
    public void bookReservation() {
        if (isBooked) {
            System.out.println("Reservation already booked for " + day + " at " + time + ".");
            return;
        }

        // find any open seat for the given day & time
        for (int i = 0; i < seating.getRows(); i++) {
            for (int j = 0; j < seating.getCols(); j++) {
                if (seating.isAvailable(day, time, i, j)) {
                    seating.reserveSeat(day, time, i, j);
                    isBooked = true;
                    System.out.println("Thank you! " +
                            ", your reservation on " + day + " at " + time + " is confirmed!");
                    return;
                }
            }
        }

        System.out.println("No available seats for " + day + " at " + time + ".");
    }

    public void cancelReservation() {
        if (!isBooked) {
            System.out.println("There is no reservation to cancel.");
        } else {
            isBooked = false;
            System.out.println("Your reservation for " + day + " at " + time + " has been canceled, " + ".");
        }
    }
}
