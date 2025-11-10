public interface SeatInterface {
    int getNumberOfSeats();
    int getRows();
    int getCols();
    boolean isAvailable(String day, String time, int row, int col);
    boolean reserveSeat(String day, String time, int row, int col);
    void displaySeats(String day, String time);
}
