import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * CS 180 team project 1
 * Client class for Restaurant Reservation System
 *
 * @author Laila
 * @version November 2024
 */
public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private boolean loggedIn = false;
    private String currentUser = null;

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        scanner = new Scanner(System.in);
        
        System.out.println("=== Restaurant Reservation System ===");
        
        
        if (!connectToServer()) {
            return;
        }

        // Main menu
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    createUser();
                    break;
                case "2":
                    removeUser();
                    break;
                case "3":
                    login();
                    break;
                case "4":
                    createReservation();
                    break;
                case "5":
                    cancelReservation();
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    closeConnection();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private boolean connectToServer() {
        try {
            System.out.print("Enter server host (localhost): ");
            String host = scanner.nextLine().trim();
            if (host.isEmpty()) host = "localhost";
            
            System.out.print("Enter server port (12345): ");
            String portStr = scanner.nextLine().trim();
            int port = portStr.isEmpty() ? 12345 : Integer.parseInt(portStr);
            
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            System.out.println("Connected to server successfully!");
            return true;
            
        } catch (Exception e) {
            System.out.println("Failed to connect to server: " + e.getMessage());
            return false;
        }
    }

    private void showMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Create User Account");
        System.out.println("2. Remove User Account");
        System.out.println("3. Login");
        System.out.println("4. Create Reservation");
        System.out.println("5. Cancel Reservation");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void createUser() {
        try {
            out.println("1"); 
            
            // Read and respond
            String prompt = in.readLine();
            System.out.println(prompt); 
            String username = scanner.nextLine();
            out.println(username);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String password = scanner.nextLine();
            out.println(password);
            
            // Read result
            String result = in.readLine();
            System.out.println(result);
            
        } catch (IOException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private void removeUser() {
        try {
            out.println("2"); // removing user
            
            String prompt = in.readLine();
            System.out.println(prompt); 
            String username = scanner.nextLine();
            out.println(username);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String password = scanner.nextLine();
            out.println(password);
            
            String result = in.readLine();
            System.out.println(result);
            
        } catch (IOException e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }

    private void login() {
        try {
            out.println("3"); // logging in
            
            String prompt = in.readLine();
            System.out.println(prompt); 
            String username = scanner.nextLine();
            out.println(username);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String password = scanner.nextLine();
            out.println(password);
            
            String result = in.readLine();
            System.out.println(result);
            
            if (result.contains("successful")) {
                loggedIn = true;
                currentUser = username;
            }
            
        } catch (IOException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private void createReservation() {
        if (!loggedIn) {
            System.out.println("Please login first!");
            return;
        }
        
        try {
            out.println("4"); // creating reservation
            
            String prompt = in.readLine();
            System.out.println(prompt); 
            String time = scanner.nextLine();
            out.println(time);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String day = scanner.nextLine();
            out.println(day);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String partySize = scanner.nextLine();
            out.println(partySize);
            
            String result = in.readLine();
            System.out.println(result);
            
        } catch (IOException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
        }
    }

    private void cancelReservation() {
        if (!loggedIn) {
            System.out.println("Please login first!");
            return;
        }
        
        try {
            out.println("5"); // canceling reservation
            
            String prompt = in.readLine();
            System.out.println(prompt); 
            String day = scanner.nextLine();
            out.println(day);
            
            prompt = in.readLine();
            System.out.println(prompt); 
            String time = scanner.nextLine();
            out.println(time);
            
            String result = in.readLine();
            System.out.println(result);
            
        } catch (IOException e) {
            System.out.println("Error canceling reservation: " + e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}