/**
 * CS 180 Project 4
 *
 * @author Laila Lone
 * @version October 30, 2025
 */
public class Seating implements SeatInterface {
    private static final int ROWS = 10;  
    private static final int COLS = 10;  
    private boolean[][] seats;

    public Seating() {
        this.seats = new boolean[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                seats[i][j] = true;
            }
        }
    }

    @Override
    public int getNumberOfSeats() {
        return ROWS * COLS;
    }

    @Override
    public int getRows() {
        return ROWS;
    }

    @Override
    public int getCols() {
        return COLS;
    }

    @Override
    public boolean isAvailable(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            System.out.println("Invalid seat location: (" + row + ", " + col + ")");
            return false;
        }
        return seats[row][col];
    }

    @Override
    public boolean reserveSeat(int row, int col) {
        if (!isAvailable(row, col)) {
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                System.out.println("Seat (" + row + ", " + col + ") is already taken.");
            }
            return false;
        }

        seats[row][col] = false;
        System.out.println("Seat (" + row + ", " + col + ") successfully reserved.");
        return true;
    }

    @Override
    public void displaySeats() {
        System.out.println("[O] = Open   [X] = Taken\n");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(seats[i][j] ? "[O]" : "[X]");
            }
            System.out.println();
        }
    }
}
