public class TestCases{
 /*
 * 
 * Verify all fields, constructors, and methods function properly.
 * Methods and constructors should have error tests to verify they do not crash when receiving invalid input, where applicable.
 * Data that persists should be validated with appropriate test cases. 
 * 
 * 
*/
//main method that creates objects to test the function methods with error checks 
    public static void main(String[] args) {

        //tests if reservation can be made and tests for errors in making reservation
        Seating seating = new Seating(3, 5);
        Reservation r1 = new Reservation(7, "Friday", 4, seating);

        r1.viewOpenSeats();
        r1.bookReservation();
        r1.viewOpenSeats();
        r1.cancelReservation();

        //comment

        User pete = new User(purdue, pete);

        

    }


}