import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Super basic test class for Seating.java
 * testing some main stuff like seat reserving, checking availibility, etc
 * not too fancy just making sure things work kinda right lol
 */

/*
 * CS 180 Team Project
 * ReservationTest
 *
 * Super basic test class for Seating.java
 * testing some main stuff like seat reserving, checking availibility, etc
 *
 * @author Jiyara Bhatia
 */ 

public class SeatingTest {

    private Seating seating;

    @BeforeEach
    void setUp() {
        // before each test just make a fresh seating object so seats reset
        seating = new Seating();
    }

    @Test
    void testGetRowsAndCols() {
      
        // just checking the number of rows and cols match what we set in class
        assertEquals(10, seating.getRows(), "Rows should be 10");
      
        assertEquals(10, seating.getCols(), "Cols should be 10");
      
    }

    @Test
    void testGetNumberOfSeats() {
        // 10 * 10 = 100 seats total, should match
        assertEquals(100, seating.getNumberOfSeats(), "Total seats should be 100");
      
    }

    @Test
    void testInvalidDayOrTimeReturnsFalse() {
        // giving wrong day/time should return false
        boolean result = seating.isAvailable("NotADay", "10:00AM", 1, 1);
      
        assertFalse(result, "Invalid day should return false");

        result = seating.isAvailable("Monday", "25:00PM", 1, 1);
        assertFalse(result, "Invalid time should return false");
      
    }

    @Test
    void testReserveAndCheckSeat() {
        // this one checks if a seat becomes unavailable after being reserved
        String day = "Monday";
        String time = "10:00AM";

        boolean availableBefore = seating.isAvailable(day, time, 2, 3);
      
        assertTrue(availableBefore, "Seat should be available before reserving");

        boolean reserved = seating.reserveSeat(day, time, 2, 3);
      
        assertTrue(reserved, "Reservation should succeed");

        boolean availableAfter = seating.isAvailable(day, time, 2, 3);
      
        assertFalse(availableAfter, "Seat should not be available after reservation");
    }

    @Test
    void testReserveSameSeatTwice() {
      
        // we tryna book the same seat again, should fail the 2nd time
        String day = "Tuesday";
        String time = "3:00PM";

        boolean firstReserve = seating.reserveSeat(day, time, 1, 1);
      
        assertTrue(firstReserve, "First reservation should work");
      

        boolean secondReserve = seating.reserveSeat(day, time, 1, 1);
      
        assertFalse(secondReserve, "Second reservation should fail cuz seat taken");
    }

    @Test
    void testReserveSeatInvalidCoords() {
        // seat outside range should not work
        String day = "Friday";
        String time = "2:00PM";

        boolean result = seating.isAvailable(day, time, -1, 5);
      
        assertFalse(result, "Negative row should be invalid");

        result = seating.isAvailable(day, time, 5, 20);
        assertFalse(result, "Column outside range should be invalid");
    }

    @Test
    void testDayAndTimeIndexPrivateMethodsIndirectly() {
        // indirectly checking day/time index works by valid reservation
        boolean reserved = seating.reserveSeat("Wednesday", "5:00PM", 5, 5);
      
        assertTrue(reserved, "Should be able to reserve with valid day/time combo");
    }
}
