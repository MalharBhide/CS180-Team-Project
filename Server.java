import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private static final Seating seating = new Seating();
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started on port 12345");
            User.loadUsers();
            User.loadReservations();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(Socket socket) {
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
                            synchronized (User.getUserList()) {
                                for (User u : User.getUserList()) {
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
                        User.addUser(new User(username, password));
                        out.println("User created successfully!");
                        break;

                    case "2": // Remove User
                        out.println("Enter Username to remove:");
                        String userToRemove = in.readLine();
                        out.println("Enter Password:");
                        String passToRemove = in.readLine();
                        boolean removed = false;
                        synchronized (User.getUserList()) {
                            for (int i = 0; i < User.getUserList().size(); i++) {
                                User u = User.getUserList().get(i);
                                if (u.getUsername().equals(userToRemove) &&
                                    u.getPassword().equals(passToRemove)) {
                                    User.removeUser(u);
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
                        synchronized (User.getUserList()) {
                            for (User u : User.getUserList()) {
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
                        out.println("Enter reservation time (e.g., 6:00PM):");
                        String time = in.readLine();
                        out.println("Enter day:");
                        String day = in.readLine();
                        int partySize = 0;
                        while (true) {
                            out.println("Enter party size:");
                            try {
                                partySize = Integer.parseInt(in.readLine());
                                if (partySize > 0) break;
                            } catch (NumberFormatException e) {}
                        }

                        Reservation newReservation = new Reservation(time, day, partySize, seating);
                        newReservation.setUsername(loggedInUser);
                        newReservation.bookReservation();
                        User.addReservation(newReservation);
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
                        synchronized (User.getReservations()) {
                            for (int i = 0; i < User.getReservations().size(); i++) {
                                Reservation r = User.getReservations().get(i);
                                if (r.getDay().equalsIgnoreCase(cancelDay) &&
                                    r.getTime().equalsIgnoreCase(cancelTime) &&
                                    loggedInUser.equals(r.getUsername())) {
                                    r.cancelReservation();
                                    User.removeReservation(r);
                                    found = true;
                                    break;
                                }
                            }
                        }
                        out.println(found ? "Reservation canceled successfully!"
                                          : "No reservation found for that time/day.");
                        break;

                    case "6": // Exit
                        out.println("Exiting the system. Goodbye!");
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

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
