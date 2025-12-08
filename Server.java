/**
import java.io.*;
import java.net.*;
import java.util.List;

 Team 1 Project 
 * * Server class that handles multiple client connections for user and reservation management. 
 * 
 * @author Malhar Bhide 
 * @version Nov 24th, 2025 
public class Server implements Runnable, ServerInterface {

    private Database db = new Database();      // database instance
    private static final Seating seating = new Seating();

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        // Load initial data
        db.loadUsers();
        db.loadReservations();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started on port 12345");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClient(Socket socket) {
        boolean loginSuccess = false;
        String loggedInUser = null;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String option;
            while ((option = in.readLine()) != null) {
                switch (option) {

                    case "1": // Create User
                        out.println("Enter a Username:");
                        String username = in.readLine();
                        while (true) {
                            boolean duplicate = false;
                            synchronized (db.getUserList()) {
                                for (User u : db.getUserList()) {
                                    if (u.getUsername().equals(username)) {
                                        duplicate = true;
                                        break;
                                    }
                                }
                            }
                            if (!duplicate) break;
                            out.println("Username already exists. Enter again:");
                            username = in.readLine();
                        }
                        out.println("Enter a Password:");
                        String password = in.readLine();
                        db.addUser(new User(username, password));
                        out.println("User created successfully!");
                        break;

                    case "2": // Remove User
                        out.println("Enter Username to remove:");
                        String userToRemove = in.readLine();
                        out.println("Enter Password:");
                        String passToRemove = in.readLine();

                        boolean removed = false;
                        synchronized (db.getUserList()) {
                            List<User> users = db.getUserList();
                            for (int i = 0; i < users.size(); i++) {
                                User u = users.get(i);
                                if (u.getUsername().equals(userToRemove) &&
                                    u.getPassword().equals(passToRemove)) {
                                    db.removeUser(u);
                                    removed = true;
                                    break;
                                }
                            }
                        }
                        out.println(removed ? "User removed successfully!" 
                                            : "User not found or incorrect password.");
                        break;

                    case "3": // Login
                        out.println("Enter your Username:");
                        String loginUsername = in.readLine();
                        out.println("Enter your Password:");
                        String loginPassword = in.readLine();

                        loginSuccess = false;
                        synchronized (db.getUserList()) {
                            for (User u : db.getUserList()) {
                                if (u.getUsername().equals(loginUsername) &&
                                    u.getPassword().equals(loginPassword)) {
                                    loginSuccess = true;
                                    loggedInUser = loginUsername;
                                    break;
                                }
                            }
                        }
                        out.println(loginSuccess ? "Login successful! Welcome, " + loggedInUser + "!"
                                                 : "Login failed. Incorrect username or password.");
                        break;

                    case "4": // Create Reservation
                        if (!loginSuccess) {
                            out.println("User is not logged in. Please log in first.");
                            break;
                        }

                        out.println("Enter reservation time (e.g., 6:00pm) Our hours are 9:00am-9:00pm:");
                        String time = in.readLine();
                        out.println("Enter day (You can book up to 7 days in advance Monday-Sunday):");
                        String day = in.readLine();
                        int partySize = 0;
                        out.println("Enter party size:");
                        while (true) {
                            try {
                                partySize = Integer.parseInt(in.readLine());
                                if (partySize > 0) break;
                            } catch (NumberFormatException e) {
                                out.println("Invalid number. Enter party size again:");
                            }
                        }

                        Reservation newReservation = new Reservation(time, day, partySize, seating);
                        newReservation.setUsername(loggedInUser);
                        newReservation.bookReservation();
                        db.addReservation(newReservation);
                        out.println("Reservation created successfully!");
                        seating.displaySeats(day, time);
                        break;

                    case "5": // Cancel Reservation
                        if (!loginSuccess) {
                            out.println("User is not logged in. Please log in first.");
                            break;
                        }

                        out.println("Enter reservation day:");
                        String cancelDay = in.readLine();
                        out.println("Enter reservation time:");
                        String cancelTime = in.readLine();

                        boolean found = false;
                        synchronized (db.getReservations()) {
                            List<Reservation> reservations = db.getReservations();
                            for (int i = 0; i < reservations.size(); i++) {
                                Reservation r = reservations.get(i);
                                if (r.getDay().equalsIgnoreCase(cancelDay) &&
                                    r.getTime().equalsIgnoreCase(cancelTime) &&
                                    loggedInUser.equals(r.getUsername())) {
                                    r.cancelReservation();
                                    db.removeReservation(r);
                                    found = true;
                                    break;
                                }
                            }
                        }
                        out.println(found ? "Reservation canceled successfully!" 
                                          : "No reservation found for that time/day.");
                        break;

                    case "6": // Exit
                        out.println("Exiting. Goodbye!");
                        socket.close();
                        return;

                    default:
                        out.println("Invalid option. Try again.");
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}
*/
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Team 1 Project
 * Server class that handles multiple client connections for user and reservation management.
 *
 * @author Malhar Bhide
 * @version Nov 24th, 2025
 */
public class Server implements Runnable, ServerInterface {

    private Database db = new Database();
    private static final Seating seating = new Seating();

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        db.loadUsers();
        db.loadReservations();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started on port 12345");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClient(Socket socket) {
        boolean loginSuccess = false;
        String loggedInUser = null;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String option;
            while ((option = in.readLine()) != null) {
                switch (option) {

                    case "1": // Create User
                        out.println("Enter a Username:");
                        String username = in.readLine();
                        while (true) {
                            boolean duplicate = false;
                            synchronized (db.getUserList()) {
                                for (User u : db.getUserList()) {
                                    if (u.getUsername().equals(username)) {
                                        duplicate = true;
                                        break;
                                    }
                                }
                            }
                            if (!duplicate) break;
                            out.println("Username already exists. Enter again:");
                            username = in.readLine();
                        }
                        out.println("Enter a Password:");
                        String password = in.readLine();
                        db.addUser(new User(username, password));
                        out.println("User created successfully!");
                        break;

                    case "2": // Remove User
                        out.println("Enter Username to remove:");
                        String userToRemove = in.readLine();
                        out.println("Enter Password:");
                        String passToRemove = in.readLine();

                        boolean removed = false;
                        synchronized (db.getUserList()) {
                            List<User> users = db.getUserList();
                            for (int i = 0; i < users.size(); i++) {
                                User u = users.get(i);
                                if (u.getUsername().equals(userToRemove) &&
                                        u.getPassword().equals(passToRemove)) {
                                    db.removeUser(u);
                                    removed = true;
                                    break;
                                }
                            }
                        }
                        out.println(removed ? "User removed successfully!"
                                : "User not found or incorrect password.");
                        break;

                    case "3": // Login
                        out.println("Enter your Username:");
                        String loginUsername = in.readLine();
                        out.println("Enter your Password:");
                        String loginPassword = in.readLine();

                        loginSuccess = false;
                        synchronized (db.getUserList()) {
                            for (User u : db.getUserList()) {
                                if (u.getUsername().equals(loginUsername) &&
                                        u.getPassword().equals(loginPassword)) {
                                    loginSuccess = true;
                                    loggedInUser = loginUsername;
                                    break;
                                }
                            }
                        }
                        out.println(loginSuccess ? "Login successful! Welcome, " + loggedInUser + "!"
                                : "Login failed. Incorrect username or password.");
                        break;

                    case "4": // Create Reservation (with automatic seat assignment)
                        if (!loginSuccess) {
                            out.println("User is not logged in. Please log in first.");
                            break;
                        }

                        out.println("Enter reservation time (e.g., 6:00PM) Our hours are 9:00AM-9:00PM:");
                        String time = in.readLine();
                        if (time == null || time.trim().isEmpty()) {
                            out.println("Error: Invalid time.");
                            break;
                        }

                        out.println("Enter day:");
                        String day = in.readLine();
                        if (day == null || day.trim().isEmpty()) {
                            out.println("Error: Invalid day.");
                            break;
                        }

                        int partySize = 0;
                        out.println("Enter party size:");
                        String partySizeStr = in.readLine();
                        try {
                            partySize = Integer.parseInt(partySizeStr);
                            if (partySize <= 0) {
                                out.println("Error: Party size must be positive.");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            out.println("Error: Invalid party size.");
                            break;
                        }

                        // Check if there are available seats
                        boolean hasAvailableSeats = false;
                        for (int i = 0; i < seating.getRows() && !hasAvailableSeats; i++) {
                            for (int j = 0; j < seating.getCols(); j++) {
                                if (seating.isAvailable(day, time, i, j)) {
                                    hasAvailableSeats = true;
                                    break;
                                }
                            }
                        }

                        if (!hasAvailableSeats) {
                            out.println("Error: No available seats for " + day + " at " + time + ".");
                            break;
                        }

                        Reservation newReservation = new Reservation(time, day, partySize, seating);
                        newReservation.setUsername(loggedInUser);
                        newReservation.bookReservation();
                        db.addReservation(newReservation);

                        if (newReservation.isBooked()) {
                            out.println("Success! Reservation created for " + day + " at " + time +
                                    ". Seat: (" + newReservation.getSeatRow() + "," +
                                    newReservation.getSeatCol() + ")");
                        } else {
                            out.println("Error: Could not create reservation.");
                        }
                        break;

                    case "5": // Cancel Reservation
                        if (!loginSuccess) {
                            out.println("User is not logged in. Please log in first.");
                            break;
                        }

                        out.println("Enter reservation day:");
                        String cancelDay = in.readLine();
                        out.println("Enter reservation time:");
                        String cancelTime = in.readLine();

                        boolean found = false;
                        synchronized (db.getReservations()) {
                            List<Reservation> reservations = db.getReservations();
                            for (int i = 0; i < reservations.size(); i++) {
                                Reservation r = reservations.get(i);
                                if (r.getDay().equalsIgnoreCase(cancelDay) &&
                                        r.getTime().equalsIgnoreCase(cancelTime) &&
                                        loggedInUser.equals(r.getUsername())) {
                                    r.cancelReservation(seating);
                                    db.removeReservation(r);
                                    found = true;
                                    break;
                                }
                            }
                        }
                        out.println(found ? "Reservation canceled successfully!"
                                : "No reservation found for that time/day.");
                        break;

                    case "6": // View Seats
                        if (!loginSuccess) {
                            out.println("User is not logged in. Please log in first.");
                            break;
                        }

                        out.println("Enter day:");
                        String viewDay = in.readLine();
                        out.println("Enter time (e.g., 6:00PM):");
                        String viewTime = in.readLine();

                        // Client will now handle loading individual seats
                        break;

                    case "checkSeat": // NEW: Check if a specific seat is available
                        String checkDay = in.readLine();
                        String checkTime = in.readLine();
                        int checkRow = Integer.parseInt(in.readLine());
                        int checkCol = Integer.parseInt(in.readLine());

                        boolean isTaken = !seating.isAvailable(checkDay, checkTime, checkRow, checkCol);
                        out.println(isTaken);
                        break;

                    case "reserveSeat": // NEW: Reserve a specific seat
                        if (!loginSuccess) {
                            out.println("Error: User is not logged in.");
                            break;
                        }

                        String resDay = in.readLine();
                        String resTime = in.readLine();
                        int resRow = Integer.parseInt(in.readLine());
                        int resCol = Integer.parseInt(in.readLine());

                        if (seating.isAvailable(resDay, resTime, resRow, resCol)) {
                            // Seat is available - reserve it
                            boolean reserved = seating.reserveSeat(resDay, resTime, resRow, resCol);
                            if (reserved) {
                                Reservation seatReservation = new Reservation(resTime, resDay, 1, seating);
                                seatReservation.setUsername(loggedInUser);
                                seatReservation.setSeatLocation(resRow, resCol);
                                seatReservation.setBooked(true);
                                db.addReservation(seatReservation);

                                out.println("Success! Seat (" + resRow + "," + resCol + ") reserved for " +
                                        resDay + " at " + resTime);
                            } else {
                                out.println("Error: Failed to reserve seat.");
                            }
                        } else {
                            // Seat is taken - check if it belongs to this user
                            boolean cancelled = false;
                            synchronized (db.getReservations()) {
                                List<Reservation> reservations = db.getReservations();
                                for (int i = 0; i < reservations.size(); i++) {
                                    Reservation r = reservations.get(i);
                                    if (r.getDay().equalsIgnoreCase(resDay) &&
                                            r.getTime().equalsIgnoreCase(resTime) &&
                                            r.getSeatRow() == resRow &&
                                            r.getSeatCol() == resCol &&
                                            loggedInUser.equals(r.getUsername())) {
                                        // This is the user's reservation - cancel it
                                        r.cancelReservation(seating);
                                        db.removeReservation(r);
                                        cancelled = true;
                                        break;
                                    }
                                }
                            }

                            if (cancelled) {
                                out.println("Cancelled! Seat (" + resRow + "," + resCol + ") is now available.");
                            } else {
                                out.println("Error: Seat is reserved by another user.");
                            }
                        }
                        break;

                    case "7": // Exit
                        out.println("Exiting. Goodbye!");
                        socket.close();
                        return;

                    default:
                        out.println("Invalid option. Try again.");
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}
