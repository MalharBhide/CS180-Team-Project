/**
 * Team 1 Project
 * Seating Interface that is implemented by Seating class
 *
 * @author Laila Lone
 * 
 * @version November 10th, 2025
 */
public interface SeatInterface {
    int getNumberOfSeats();
    int getRows();
    int getCols();
    boolean isAvailable(String day, String time, int row, int col);
    boolean reserveSeat(String day, String time, int row, int col);
    void displaySeats(String day, String time);
}
