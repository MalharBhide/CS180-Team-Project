/*
 * CS 180 Team Project
 *
 *
 *
 * @author Himangi Nepal
 * @version 1.0
 */

public class Reservation {

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

    // Ability to select a day for the reservation
    // Ability to select a time for the reservation
    // Ability to view all open seats at the given time
    // Ability to book varying party sizes
    // View pricing - if applicable
    // Cancel reservations

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

            } // end 2nd for loop
        } // end 1st for loop
    } // end method

    public void cancelReservation() {

        if (!isBooked) {
            System.out.println("There is no reservation to cancel.");
            return;
        }
        else {
            isBooked = false;
            System.out.println("Reservation successfully canceled for " + day + " at " + time); //need to add string formating for time
        }
    }

    // Main for testing
    public static void main(String[] args) {
        Seating seating = new Seating(3, 5);
        Reservation r1 = new Reservation(7, "Friday", 4, seating);

        r1.viewOpenSeats();
        r1.bookReservation();
        r1.viewOpenSeats();
        r1.cancelReservation();

        //comment
        

    }
}
