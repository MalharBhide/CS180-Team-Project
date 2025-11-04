public class Seating {
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

        public boolean isAvailable(int row, int col) {
            if (row < 0 || row >= rows || col < 0 || col >= cols)
                return false;
            return seats[row][col];
        }

        public boolean reserveSeat(int row, int col) {
            if (isAvailable(row, col)) {
                seats[row][col] = false;
                return true;
            }
            return false;
        }

        public void displaySeats() {
            System.out.println("[O] = Open   [X] = Taken\n");  
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(seats[i][j] ? "[O]" : "[X]"); //X represents the taken seats and O represents the open ones
                }
                System.out.println();
            }
        }
}




