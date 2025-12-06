import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

/**
 * CS 180 team project 1
 * Client class for Restaurant Reservation System
 *
 * @author Laila
 * @version November 2024
 */
public class Client implements ClientInterface {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean loggedIn = false;
    private String currentUser = null;
    private JFrame frame; 

    public static void main(String[] args) {
        new Client().start();
    }
    public void run() {
        JFrame frame = new JFrame("Restaurant Reservation System Client");
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(e -> createUser());
        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(e -> removeUser());
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        JButton createReservationButton = new JButton("Create Reservation");
        createReservationButton.addActionListener(e -> createReservation());
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(e -> cancelReservation());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            closeConnection();
            frame.dispose();
        });
        panel.add(createUserButton);
        panel.add(removeUserButton);
        panel.add(loginButton);
        panel.add(createReservationButton);
        panel.add(cancelReservationButton);
        panel.add(closeButton);
        frame.add(panel);
    }
    public void start() {
        JOptionPane.showMessageDialog(
            null,
            "Restaurant Reservation System",
            "Welcome",
            JOptionPane.INFORMATION_MESSAGE
        );
        if (!connectToServer()) {
            JOptionPane.showMessageDialog(
                null, 
                "Failed to connect to server.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        run();
    }

    public boolean connectToServer() {
        try {
            String host = JOptionPane.showInputDialog(
                null,
                "Enter server host (localhost):",
                "Server Connection",
                JOptionPane.QUESTION_MESSAGE
            );
            if (host == null) {
                return false;
            }
            if (host.trim().isEmpty()) {
                host = "localhost";
            }
            String portStr = JOptionPane.showInputDialog(
                null,
                "Enter server port (1245):",
                "Server Connection",
                JOptionPane.QUESTION_MESSAGE
            );

            if (portStr == null) {
                return false;
            }
            int port = portStr.isEmpty() ? 12345 : Integer.parseInt(portStr);
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            JOptionPane.showMessageDialog(
                null,
                "Connected to server successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to connect to server: " + e.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    public void createUser() {
   JTextField usernameField = new JTextField();
   JPasswordField passwordField = new JPasswordField();


   Object[] message = {
           "Username:", usernameField,
           "Password:", passwordField
   };


   int option = JOptionPane.showConfirmDialog(
           null,
           message,
           "Create User",
           JOptionPane.OK_CANCEL_OPTION
   );


   if (option == JOptionPane.OK_OPTION) {
       try {
            out.println("1"); 

            in.readLine();
            out.println(usernameField.getText());
            in.readLine();
            out.println(new String(passwordField.getPassword()));
            String result = in.readLine();
            JOptionPane.showMessageDialog(null, result);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
       }
   }
}


private void removeUser() {
   JTextField usernameField = new JTextField();
   JPasswordField passwordField = new JPasswordField();


   Object[] message = {
           "Username:", usernameField,
           "Password:", passwordField
   };


   int option = JOptionPane.showConfirmDialog(
           null,
           message,
           "Delete User",
           JOptionPane.OK_CANCEL_OPTION
   );


   if (option == JOptionPane.OK_OPTION) {
       try {
            out.println("2"); // delete user
            in.readLine();
            out.println(usernameField.getText());
            in.readLine();
            out.println(new String(passwordField.getPassword()));
            String result = in.readLine();
            JOptionPane.showMessageDialog(null, result);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
       }
   }
}
    public void login() {
    try {
        out.println("3");

        String prompt = in.readLine();
        String username = JOptionPane.showInputDialog(null, prompt, "Login", JOptionPane.QUESTION_MESSAGE);
        

        if (username == null) return; 

        out.println(username);


        prompt = in.readLine();
        String password = JOptionPane.showInputDialog(null, prompt, "Login", JOptionPane.QUESTION_MESSAGE);
        

        if (password == null) return; 
        
        out.println(password);

 
        String result = in.readLine();

        JOptionPane.showMessageDialog(null, result, "Login Status", JOptionPane.INFORMATION_MESSAGE);

        if (result.contains("successful")) {
            loggedIn = true; 
            currentUser = username;
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(
            null, 
            "Error during login: " + e.getMessage(),
            "Login Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
}

public void createReservation() {
    if (!loggedIn) {
        JOptionPane.showMessageDialog(null, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        out.println("4");

        String prompt = in.readLine();
        String time = JOptionPane.showInputDialog(null, prompt);
        out.println(time);

        prompt = in.readLine();
        String day = JOptionPane.showInputDialog(null, prompt);
        out.println(day);

        prompt = in.readLine();
        String partySize = JOptionPane.showInputDialog(null, prompt);
        out.println(partySize);

        String result = in.readLine();
        JOptionPane.showMessageDialog(null, result);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error creating reservation: " + e.getMessage());
    }
}

public void cancelReservation() {
    if (!loggedIn) {
        JOptionPane.showMessageDialog(null, "Please login first!");
        return;
    }

    try {
        out.println("5");

        String prompt = in.readLine();
        String day = JOptionPane.showInputDialog(null, prompt);
        out.println(day);

        prompt = in.readLine();
        String time = JOptionPane.showInputDialog(null, prompt);
        out.println(time);

        String result = in.readLine();
        JOptionPane.showMessageDialog(null, result);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error canceling reservation: " + e.getMessage());
    }
}

public void closeConnection() {
    try {
        if (out != null) out.close();
        if (in != null) in.close();
        if (socket != null) socket.close();
    } catch (IOException e) {
        // Ignore
    }
}
}