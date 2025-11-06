/**
 * CS 180 project 4
 *
 * @author Laila Lone
 * @version 1.0
 */
public class Seating implements SeatInterface {
    private int rows;
    private int cols;
    private boolean[][] seats;

    public Seating(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.seats = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = true;
            }
        }
    }

    public int getNumberOfSeats() {
        return rows * cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isAvailable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            System.out.println("Invalid seat location: (" + row + ", " + col + ")");
            return false;
        }
        return seats[row][col];
    }

    public boolean reserveSeat(int row, int col) {
        if (!isAvailable(row, col)) {
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                System.out.println("Seat (" + row + ", " + col + ") is already taken.");
            }
            return false;
        }

        seats[row][col] = false;
        System.out.println("Seat (" + row + ", " + col + ") successfully reserved.");
        return true;
    }

    public void displaySeats() {
        System.out.println("[O] = Open   [X] = Taken\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(seats[i][j] ? "[O]" : "[X]");
            }
            System.out.println();
        }
    }
}

