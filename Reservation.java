/*
 * CS 180 Team Project
 *
 * @author Himangi Nepal
 * @version 1.1
 */

import java.util.Scanner;
import java.io.*;

public class Reservation implements ReservationInterface, Serializable {

    private String time; // changed int to String to allow am/pm input
    private String day;
    private int partySize;
    private transient Seating seating; //make transient to fix error in saving objects, ignores field when saving because the seating is the same across all objects
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
        seating.displaySeats(day, time);
    }

    // modified so that only one seat is reserved per request
    public void bookReservation() {
        if (isBooked) {
            System.out.println("Reservation already booked for " + day + " at " + time + ".");
            return;
        }

        // iterate through 2D array to find ONE available seat
        for (int i = 0; i < seating.getRows(); i++) {
            for (int j = 0; j < seating.getCols(); j++) {

                if (seating.isAvailable(day, time, i, j)) {
                    seating.reserveSeat(day, time, i, j);
                    isBooked = true;
                    System.out.println("Thank you! Your reservation on "
                            + day + " at " + time + " is confirmed.");
                    return;
                }
            }
        }

        System.out.println("No available seats for the selected time.");
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
