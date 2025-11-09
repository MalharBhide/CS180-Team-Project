/*
 * CS 180 Team Project
 *
 * @author Himangi Nepal
 * @version 1.0
 */

import java.util.Scanner;

public class Reservation implements ReservationInterface {

    private String time; // changed int to String to allow am/pm input
    private String day;
    private int partySize;
    private Seating seating;
    private boolean isBooked;

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

    public void viewOpenSeats() {
        seating.displaySeats();
    }

    public void bookReservation() {
        if (isBooked) {
            System.out.println("Reservation already booked for " + day + " at " + time + ".");
            return;
        }

        int seatsReserved = 0;

        // Iterate through 2D array to reserve seats
        for (int i = 0; i < seating.getRows(); i++) {
            for (int j = 0; j < seating.getCols(); j++) {

                if (seating.isAvailable(i, j)) { 
                    seating.reserveSeat(i, j); 
                    seatsReserved++;
                }

                if (seatsReserved == partySize) { 
                    isBooked = true;
                    System.out.println("Thank you! Your reservation is now confirmed for "
                            + partySize + " on " + day + " at " + time + ".");
                    return;
                }
            }
        }

        System.out.println("Not enough available seats for a party of " + partySize + ".");
    }

    public void cancelReservation() {
        if (!isBooked) {
            System.out.println("There is no reservation to cancel.");
        } else {
            isBooked = false;
            System.out.println("Your reservation for " + day + " at " + time + " has been canceled.");
        }
    }
}
