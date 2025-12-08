/**
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

 * CS 180 team project 1
 * Client class for Restaurant Reservation System
 *
 * @author Laila
 * @version November 2024
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
        frame = new JFrame("Restaurant Reservation System Client");
        frame.setSize(800, 400);          
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
                "Enter server port (12345):",
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
        JOptionPane.showMessageDialog(null, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
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
*/
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * CS 180 team project 1
 * Client class for Restaurant Reservation System
 *
 * @author Laila
 * @version November 2024 (updated with cancel functionality)
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
        frame = new JFrame("Restaurant Reservation System Client");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JPanel panel = new JPanel();

        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(e -> createUser());

        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(e -> removeUser());

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        JButton viewSeatsButton = new JButton("View Seats");
        viewSeatsButton.addActionListener(e -> viewSeats());

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
        panel.add(viewSeatsButton);
        panel.add(cancelReservationButton);
        panel.add(closeButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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

        SwingUtilities.invokeLater(this::run);
    }

    public boolean connectToServer() {
        try {
            String host = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter server host:",
                    "Server Connection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    "localhost"
            );

            if (host == null) return false;
            if (host.trim().isEmpty()) host = "localhost";

            String portStr = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter server port:",
                    "Server Connection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    "12345"
            );

            if (portStr == null) return false;
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

    public void viewSeats() {
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Thread t = new Thread(() -> {
            try {
                out.println("6");

                String promptDay = in.readLine();
                if (promptDay == null) return;

                final String[] dayHolder = new String[1];
                SwingUtilities.invokeAndWait(() -> dayHolder[0] = JOptionPane.showInputDialog(null, promptDay));
                String day = dayHolder[0];
                if (day == null) {
                    out.println("");
                    return;
                }
                out.println(day);

                String promptTime = in.readLine();
                if (promptTime == null) return;

                final String[] timeHolder = new String[1];
                SwingUtilities.invokeAndWait(() -> timeHolder[0] = JOptionPane.showInputDialog(null, promptTime));
                String time = timeHolder[0];
                if (time == null) {
                    out.println("");
                    return;
                }
                out.println(time);

                loadSeatsAndShow(day, time);

            } catch (Exception e) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Error viewing seats: " + e.getMessage()));
            }
        });
        t.start();
    }

    public void createUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = { "Username:", usernameField, "Password:", passwordField };

        int option = JOptionPane.showConfirmDialog(
                null, message, "Create User", JOptionPane.OK_CANCEL_OPTION
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

    public void removeUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = { "Username:", usernameField, "Password:", passwordField };

        int option = JOptionPane.showConfirmDialog(
                null, message, "Delete User", JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                out.println("2");
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
            if (username == null) {
                out.println("");
                return;
            }
            out.println(username);

            prompt = in.readLine();
            String password = JOptionPane.showInputDialog(null, prompt, "Login", JOptionPane.QUESTION_MESSAGE);
            if (password == null) {
                out.println("");
                return;
            }
            out.println(password);

            String result = in.readLine();
            JOptionPane.showMessageDialog(null, result);

            if (result != null && result.contains("successful")) {
                loggedIn = true;
                currentUser = username;
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error during login: " + e.getMessage());
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
            if (time == null) {
                out.println("");
                return;
            }
            out.println(time);

            prompt = in.readLine();
            String day = JOptionPane.showInputDialog(null, prompt);
            if (day == null) {
                out.println("");
                return;
            }
            out.println(day);

            prompt = in.readLine();
            String partySize = JOptionPane.showInputDialog(null, prompt);
            if (partySize == null) {
                out.println("");
                return;
            }
            out.println(partySize);

            String result = in.readLine();
            JOptionPane.showMessageDialog(null, result);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating reservation: " + e.getMessage());
        }
    }

    public void loadSeatsAndShow(String day, String time) {
        SwingWorker<boolean[][], Void> worker = new SwingWorker<>() {
            @Override
            protected boolean[][] doInBackground() throws Exception {
                boolean[][] taken = new boolean[10][10];

                for (int r = 0; r < 10; r++) {
                    for (int c = 0; c < 10; c++) {
                        out.println("checkSeat");
                        out.println(day);
                        out.println(time);
                        out.println(r);
                        out.println(c);

                        String response = in.readLine();
                        taken[r][c] = Boolean.parseBoolean(response);
                    }
                }
                return taken;
            }

            @Override
            protected void done() {
                try {
                    boolean[][] taken = get();
                    showSeatingChart(day, time, taken);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error loading seats: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    public void showSeatingChart(String day, String time, boolean[][] takenSeats) {
        JFrame seatFrame = new JFrame("Select Your Seat - " + day + " " + time);
        seatFrame.setSize(600, 650);
        seatFrame.setLocationRelativeTo(frame);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create grid panel for seats
        JPanel gridPanel = new JPanel(new GridLayout(10, 10));

        JButton[][] buttons = new JButton[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                final int row = r;
                final int col = c;

                JButton seatBtn = new JButton("(" + row + "," + col + ")");
                seatBtn.setMargin(new Insets(1, 1, 1, 1));
                seatBtn.setOpaque(true);

                boolean taken = takenSeats[row][col];

                if (taken) {
                    seatBtn.setBackground(Color.RED);
                } else {
                    seatBtn.setBackground(Color.GREEN);
                }

                // Add action listener for both available and taken seats
                seatBtn.addActionListener(e -> {
                    seatBtn.setEnabled(false);
                    Color originalColor = seatBtn.getBackground();
                    seatBtn.setBackground(Color.LIGHT_GRAY);

                    SwingWorker<String, Void> reserveWorker = new SwingWorker<>() {
                        @Override
                        protected String doInBackground() throws Exception {
                            out.println("reserveSeat");
                            out.println(day);
                            out.println(time);
                            out.println(row);
                            out.println(col);
                            String result = in.readLine();
                            return result;
                        }

                        @Override
                        protected void done() {
                            try {
                                String result = get();
                                JOptionPane.showMessageDialog(null, result);

                                if (result != null && result.toLowerCase().contains("success")) {
                                    // Successfully reserved
                                    seatBtn.setBackground(Color.RED);
                                    seatBtn.setEnabled(true);
                                } else if (result != null && result.toLowerCase().contains("cancelled")) {
                                    // Successfully cancelled
                                    seatBtn.setBackground(Color.GREEN);
                                    seatBtn.setEnabled(true);
                                } else {
                                    // Error (seat taken by someone else)
                                    seatBtn.setBackground(originalColor);
                                    seatBtn.setEnabled(true);
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                                seatBtn.setBackground(originalColor);
                                seatBtn.setEnabled(true);
                            }
                        }
                    };

                    reserveWorker.execute();
                });

                buttons[row][col] = seatBtn;
                gridPanel.add(seatBtn);
            }
        }

        // Create bottom panel for back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> seatFrame.dispose());
        bottomPanel.add(backButton);

        // Add panels to main panel
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        seatFrame.add(mainPanel);
        seatFrame.setVisible(true);
    }

    public void showSeatingChart(String day, String time) {
        loadSeatsAndShow(day, time);
    }

    public void cancelReservation() {
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            out.println("5");

            String prompt = in.readLine();
            String day = JOptionPane.showInputDialog(null, prompt);
            if (day == null) {
                out.println("");
                return;
            }
            out.println(day);

            prompt = in.readLine();
            String time = JOptionPane.showInputDialog(null, prompt);
            if (time == null) {
                out.println("");
                return;
            }
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
            // ignore
        }
    }
}