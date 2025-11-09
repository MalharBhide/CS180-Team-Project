/*
 * CS 180 Team Project
 *
 *
 *
 * @author Himangi Nepal
 * @version 1.0
 */

import java.util.Scanner; 

public class Reservation implements ReservationInterface {

    private int time;
    private String day;
    private int partySize;
    private Seating seating;
    private boolean isBooked;

    public Reservation(int time, String day, int partySize, Seating seating) {
        this.time = time;
        this.day = day;
        this.partySize = partySize;
        this.seating = seating;
    }

    public int getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setTime(int time) {
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
        // need to add separate getter and setter methods in the seating class
        // to iterate through 2d array
        for (int i = 0; i < seating.getRows(); i++) {
            for (int j = 0; j < seating.getCols(); j++) {

                if (seating.isAvailable(i, j)) { // iterate through the array and see if the seats are available
                    seating.reserveSeat(i, j); // reserve if it's available
                    seatsReserved++;
                }

                if (seatsReserved == partySize) { // check if desired num of seats are booked
                    isBooked = true;
                    System.out.println("Thank You! Your reservation is now confirmed for "
                            + partySize + " on " + day + " at " + time);
                    return;
                }
                //write the reservation

            } // end 2nd for loop
        } // end 1st for loop
    } // end method

    public void cancelReservation() {
    if (!isBooked) {
        System.out.println("There is no reservation to cancel.");
        return;
    }

    for (int i = 0; i < seating.getRows(); i++) {
        for (int j = 0; j < seating.getCols(); j++) {
            // example logic to "free" reserved seats if needed
            if (!seating.isAvailable(i, j)) {
                // you’d add an “unreserveSeat()” method in Seating
            }
        }
    }

    isBooked = false;
    System.out.println("Reservation canceled for " + day + " at " + time); 
    }

    
}
