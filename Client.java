import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * CS 180 Project 4
 *
 * @author Laila
 * @version October 30th 2025
 */
public class Client {
    // GUI components
    private JFrame mainWindow;
    private JTextArea messageArea;
    private JTextField serverHostField, serverPortField;
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> daySelect;
    private JComboBox<String> timeSelect;
    private JTextField groupSizeField;
    private JButton connectButton, loginButton, signupButton;
    private JButton showTablesButton, reserveButton, cancelButton, logoutButton;

    // network
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    private final ExecutorService networkThread = Executors.newSingleThreadExecutor();
    private final ExecutorService responseHandler = Executors.newCachedThreadPool();
    private boolean connected = false;
    private boolean loggedIn = false;
    private String currentUser = "";

    public Client() {
        setupGUI();
    }

    /**
     * Initializes all GUI components and layouts
     */
    private void setupGUI() {
        mainWindow = new JFrame("Restaurant Reservations");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(750, 600);
        mainWindow.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = makeConnectionPanel();
        mainWindow.add(topPanel, BorderLayout.NORTH);

        JPanel loginPanel = makeLoginPanel();
        mainWindow.add(loginPanel, BorderLayout.WEST);

        JPanel reservationPanel = makeReservationPanel();
        mainWindow.add(reservationPanel, BorderLayout.SOUTH);

        JPanel messagePanel = makeMessagePanel();
        mainWindow.add(messagePanel, BorderLayout.CENTER);

        setupButtonActions();
        refreshUI();
        
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    private JPanel makeConnectionPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Server Connection"));

        JPanel serverInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        serverInfo.add(new JLabel("Host:"));
        serverHostField = new JTextField("localhost", 10);
        serverInfo.add(serverHostField);
        
        serverInfo.add(new JLabel("Port:"));
        serverPortField = new JTextField("5555", 6);
        serverInfo.add(serverPortField);
        
        connectButton = new JButton("Connect");
        serverInfo.add(connectButton);

        JPanel statusInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusInfo.add(new JLabel("Status: "));
        JLabel statusText = new JLabel("Not connected");
        statusText.setForeground(Color.RED);
        statusInfo.add(statusText);

        panel.add(serverInfo);
        panel.add(statusInfo);
        return panel;
    }

    private JPanel makeLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        panel.add(new JLabel("Username:"));
        userField = new JTextField(15);
        panel.add(userField);

        panel.add(new JLabel("Password:"));
        passField = new JPasswordField(15);
        panel.add(passField);

        JPanel buttonArea = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        signupButton = new JButton("Create Account");
        buttonArea.add(loginButton);
        buttonArea.add(signupButton);

        panel.add(new JLabel());
        panel.add(buttonArea);

        return panel;
    }

    private JPanel makeReservationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Make Reservation"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        daySelect = new JComboBox<>(days);
        panel.add(daySelect, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Time:"), gbc);
        gbc.gridx = 3;
        String[] times = {"9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", 
                         "3:00PM", "4:00PM", "5:00PM", "6:00PM", "7:00PM", "8:00PM", "9:00PM"};
        timeSelect = new JComboBox<>(times);
        panel.add(timeSelect, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Party Size:"), gbc);
        gbc.gridx = 5;
        groupSizeField = new JTextField("1", 3);
        panel.add(groupSizeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        showTablesButton = new JButton("View Available Tables");
        panel.add(showTablesButton, gbc);

        gbc.gridx = 2; gbc.gridwidth = 1;
        reserveButton = new JButton("Make Reservation");
        panel.add(reserveButton, gbc);

        gbc.gridx = 3;
        cancelButton = new JButton("Cancel Reservation");
        panel.add(cancelButton, gbc);

        gbc.gridx = 4; gbc.gridwidth = 2;
        logoutButton = new JButton("Logout");
        panel.add(logoutButton, gbc);

        return panel;
    }

    private JPanel makeMessagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Messages"));
        
        messageArea = new JTextArea(15, 60);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(messageArea);
        
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void setupButtonActions() {
        connectButton.addActionListener(e -> handleConnect());
        loginButton.addActionListener(e -> doLogin());
        signupButton.addActionListener(e -> createAccount());
        showTablesButton.addActionListener(e -> showAvailableTables());
        
        // Mix of lambda and inner class
        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });
        
        cancelButton.addActionListener(e -> cancelReservation());
        logoutButton.addActionListener(e -> doLogout());

        passField.addActionListener(e -> {
            if (connected) doLogin();
        });
    }

    private void refreshUI() {
        boolean canMakeReservations = connected && loggedIn;
        
        loginButton.setEnabled(connected && !loggedIn);
        signupButton.setEnabled(connected && !loggedIn);
        showTablesButton.setEnabled(connected);
        reserveButton.setEnabled(canMakeReservations);
        cancelButton.setEnabled(canMakeReservations);
        logoutButton.setEnabled(loggedIn);
        
        connectButton.setText(connected ? "Disconnect" : "Connect");
        
        if (loggedIn) {
            showMessage("Logged in as: " + currentUser);
        }
    }

    private void handleConnect() {
        if (!connected) {
            connectToServer();
        } else {
            disconnectFromServer();
        }
    }

    private void connectToServer() {
        String host = serverHostField.getText().trim();
        int port;
        
        try {
            port = Integer.parseInt(serverPortField.getText().trim());
        } catch (NumberFormatException ex) {
            showMessage("Bad port number - using 5555");
            port = 5555;
        }

        networkThread.execute(() -> {
            try {
                showMessage("Connecting to server...");
                socket = new Socket(host, port);
                socket.setSoTimeout(30000);
                
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                connected = true;
                showMessage("Connected!");
                
                SwingUtilities.invokeLater(this::refreshUI);
                startListening();
                
            } catch (ConnectException e) {
                showMessage("Can't connect - server running?");
            } catch (IOException e) {
                showMessage("Connection failed: " + e.getMessage());
            }
        });
    }

    private void disconnectFromServer() {
        networkThread.execute(() -> {
            try {
                if (loggedIn) {
                    sendRequest("LOGOUT", "{}");
                }
            } catch (Exception ignored) {}
            
            closeConnection();
            showMessage("Disconnected");
            SwingUtilities.invokeLater(this::refreshUI);
        });
    }

    private void startListening() {
        responseHandler.execute(() -> {
            try {
                String response;
                while (connected && (response = in.readLine()) != null) {
                    final String msg = response;
                    SwingUtilities.invokeLater(() -> processServerMessage(msg));
                }
            } catch (IOException e) {
                if (connected) {
                    SwingUtilities.invokeLater(() -> {
                        showMessage("Lost connection");
                        closeConnection();
                        refreshUI();
                    });
                }
            }
        });
    }

    private void processServerMessage(String message) {
        showMessage("SERVER: " + message);
        
        if (message.startsWith("LOGIN_SUCCESS")) {
            loggedIn = true;
            currentUser = userField.getText().trim();
            showMessage("Login good! Hi " + currentUser);
        } else if (message.startsWith("LOGIN_FAILED")) {
            showMessage("Login failed - try again");
        } else if (message.startsWith("USER_CREATED")) {
            showMessage("Account made! Login now.");
        } else if (message.startsWith("USER_EXISTS")) {
            showMessage("Username taken");
        } else if (message.startsWith("BOOKING_SUCCESS")) {
            showMessage("Reservation confirmed!");
        } else if (message.startsWith("BOOKING_FAILED")) {
            showMessage("Couldn't make reservation");
        } else if (message.startsWith("CANCELLATION_SUCCESS")) {
            showMessage("Reservation cancelled");
        }
        
        refreshUI();
    }

    private void sendRequest(String command, String data) {
        if (!connected || out == null) {
            showMessage("Not connected to server");
            return;
        }
        
        String request = command + "|" + data;
        out.println(request);
        showMessage("CLIENT: " + request);
    }

    private String buildRequest(String... items) {
        StringBuilder request = new StringBuilder();
        for (int i = 0; i < items.length; i += 2) {
            if (i > 0) request.append("|");
            request.append(items[i]).append("=").append(items[i + 1]);
        }
        return request.toString();
    }

    private void doLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Need username and password");
            return;
        }
        
        String data = buildRequest("username", username, "password", password);
        sendRequest("LOGIN", data);
    }

    private void createAccount() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Enter username and password");
            return;
        }
        
        String data = buildRequest("username", username, "password", password);
        sendRequest("CREATE_USER", data);
    }

    private void showAvailableTables() {
        String day = (String) daySelect.getSelectedItem();
        String time = (String) timeSelect.getSelectedItem();
        String data = buildRequest("day", day, "time", time);
        sendRequest("LIST_SEATS", data);
    }

    private void makeReservation() {
        if (!loggedIn) {
            showMessage("Please login first");
            return;
        }
        
        String day = (String) daySelect.getSelectedItem();
        String time = (String) timeSelect.getSelectedItem();
        String partySize = groupSizeField.getText().trim();
        
        try {
            int size = Integer.parseInt(partySize);
            if (size <= 0) {
                showMessage("Party size must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid party size");
            return;
        }
        
        String data = buildRequest("day", day, "time", time, "partySize", partySize);
        sendRequest("BOOK", data);
    }

    private void cancelReservation() {
        if (!loggedIn) {
            showMessage("Please login first");
            return;
        }
        
        String day = (String) daySelect.getSelectedItem();
        String time = (String) timeSelect.getSelectedItem();
        String data = buildRequest("day", day, "time", time);
        sendRequest("CANCEL", data);
    }

    private void doLogout() {
        sendRequest("LOGOUT", "{}");
        loggedIn = false;
        currentUser = "";
        showMessage("Logged out");
        refreshUI();
    }

    private void showMessage(String text) {
        SwingUtilities.invokeLater(() -> {
            String time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
            messageArea.append("[" + time + "] " + text + "\n");
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        });
    }

    private void closeConnection() {
        connected = false;
        loggedIn = false;
        currentUser = "";
        
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            // ignore
        }
        
        SwingUtilities.invokeLater(this::refreshUI);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                //ignore
            }
            new Client();
        });
    }
}