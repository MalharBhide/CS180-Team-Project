/**
 * CS 180 Project 4
 *
 * @author Laila
 * @version October 30th 2025
 */
public class Seating implements SeatInterface {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int DAYS = 7; // days in a week
    private static final int TIME_SLOTS = 13; // 9AM–9PM 

    private boolean[][][][] seats;

    private static final String[] DAYS_OF_WEEK = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    private static final String[] TIMES_OF_DAY = {
        "9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", 
        "3:00PM", "4:00PM", "5:00PM", "6:00PM", "7:00PM", "8:00PM", "9:00PM"
    };

    public Seating() {
        seats = new boolean[DAYS][TIME_SLOTS][ROWS][COLS];

        for (int d = 0; d < DAYS; d++) { // initialize all seats to available
            for (int t = 0; t < TIME_SLOTS; t++) {
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLS; c++) {
                        seats[d][t][r][c] = true;
                    }
                }
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

    public int getDays() {
        return DAYS;
    }

    public int getTimeSlots() {
        return TIME_SLOTS;
    }

    private int getDayIndex(String day) {  //gets index of day
        for (int i = 0; i < DAYS_OF_WEEK.length; i++) {
            if (DAYS_OF_WEEK[i].equalsIgnoreCase(day)) {
                return i;
            }
        }
        return -1; // not found
    }

    private int getTimeIndex(String time) {  //gets index of time
        for (int i = 0; i < TIMES_OF_DAY.length; i++) {
            if (TIMES_OF_DAY[i].equalsIgnoreCase(time)) {
                return i;
            }
        }
        return -1; 
    }

    public boolean isAvailable(String day, String time, int row, int col) {
        int d = getDayIndex(day);
        int t = getTimeIndex(time);

        if (d == -1 || t == -1) {
            System.out.println("Invalid day or time entered.");
            return false;
        }
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            System.out.println("Invalid seat location.");
            return false;
        }
        return seats[d][t][row][col];
    }

    public boolean reserveSeat(String day, String time, int row, int col) {
        int d = getDayIndex(day);
        int t = getTimeIndex(time);

        if (d == -1 || t == -1) {
            System.out.println("Invalid day or time entered.");
            return false;
        }

        if (!isAvailable(day, time, row, col)) {
            System.out.println("Seat (" + row + ", " + col + ") at " + day + " " + time + " is already taken.");
            return false;
        }

        seats[d][t][row][col] = false;
        System.out.println("Seat (" + row + ", " + col + ") reserved for " + day + " " + time + ".");
        return true;
    }

    public void displaySeats(String day, String time) {
        int d = getDayIndex(day);
        int t = getTimeIndex(time);

        if (d == -1 || t == -1) {
            System.out.println("Invalid day or time entered.");
            return;
        }

        System.out.println("\nSeat layout for " + day + " at " + time);
        System.out.println("[O] = Open table   [X] = Taken table\n");

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(seats[d][t][i][j] ? "[O]" : "[X]");
            }
            System.out.println();
        }
    }
}
