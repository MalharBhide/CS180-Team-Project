import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * ReservationTest class for CS 180 Team Project.
 * 
 * Tests the Reservation class for correct behavior including:
 *  -getters and setters
 *  -booking a reservation
 *  -canceling a reservation
 * 
 * 
 * @author Jiyara Bhatia
 * @version 1.0
 */
public class ReservationTest {
   
    private Reservation reservation;
    private MockSeating seating; 
    /** 
    * Mockseating class is a class that helps test the functions of the reservation class.
    *
    * @author Jiyara Bhatia
    * @version 1.0
    */
    private static class MockSeating extends Seating {
        private boolean[][] seats;
        private int rows;
        private int cols;

        public MockSeating(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            seats = new boolean[rows][cols]; // false means empty seat
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }

        // check if a seat is open or not
        public boolean isAvailable(int row, int col) {
            return !seats[row][col];
        }

        // mark a seat as taken
        public boolean reserveSeat(int row, int col) {
            seats[row][col] = true;
            return true;
        }

        // dont need this for test just placeholder
        public void displaySeats() { }
    }

    @BeforeEach
    public void setUp() {
        // before every test we make a new seating and reservation
        seating = new MockSeating(2, 2); // 2x2 lil grid of seats
        reservation = new Reservation("7:00 PM", "Friday", 4, seating);
    }

    @Test
    public void testGetters() {
        // making sure the getters return what we gave it
        assertEquals("7:00 PM", reservation.getTime());
        assertEquals("Friday", reservation.getDay());
        assertEquals(4, reservation.getPartySize());
    }

    @Test
    public void testSetters() {
        // change values and see if they updated
        reservation.setTime("8:00 PM");
        reservation.setDay("Saturday");
        reservation.setPartySize(2);

        assertEquals("8:00 PM", reservation.getTime());
        assertEquals("Saturday", reservation.getDay());
        assertEquals(2, reservation.getPartySize());
    }

    @Test
    public void testBookReservationSuccess() {
        // trying to book one seat and checking if it actually worked
        reservation.bookReservation();
        assertTrue(seating.seats[0][0]); // should mark that first seat as reserved
    }

    @Test
    public void testBookReservationAlreadyBooked() {
        // booking twice should not make a new one
        reservation.bookReservation();
        reservation.bookReservation(); // trying again to see if it blocks it
        assertTrue(seating.seats[0][0]); // still true, nothing broke
    }

    @Test
    public void testCancelReservationWithoutBooking() {
        // trying to cancel before booking, should just print message
        reservation.cancelReservation();
        boolean anyReserved = false;
        for (int i = 0; i < seating.getRows(); i++) {
            for (int j = 0; j < seating.getCols(); j++) {
                if (seating.seats[i][j]) {
                    anyReserved = true;
                }
            }
        }
        assertFalse(anyReserved); // none should be booked
    }

    @Test
    public void testCancelReservationAfterBooking() {
        // book it, cancel it, and then see if we can book again after
        reservation.bookReservation();
        reservation.cancelReservation();
        reservation.bookReservation(); // should work again
        assertTrue(seating.seats[0][0]); // seat should be reserved again
    }

    @AfterEach
    public void remove() {
        // cleanup after every test just to be safe
        reservation = null;
        seating = null;
    }
}
