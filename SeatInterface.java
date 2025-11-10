public interface SeatInterface {
    int getNumberOfSeats();
    int getRows();
    int getCols();
    boolean isAvailable(int row, int col);
    boolean reserveSeat(int row, int col);
    void displaySeats();
}
