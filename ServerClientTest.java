import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test class for Server and Client communication
 *
 * @author Jiyara Bhatia
 * @version November 2024
 */
class ServerClientTest {

    private static Thread serverThread;
    private static final int TEST_PORT = 12345;
    private static final String TEST_HOST = "localhost";

    @BeforeAll
    static void startServer() throws InterruptedException {
        // delete old test data files
        new File("users.dat").delete();
        new File("reservations.dat").delete();

        // start server
        serverThread = new Thread(new Server());
        serverThread.setDaemon(true);
        serverThread.start();

        // wait for server to start
        Thread.sleep(2000);
    }

    @AfterAll
    static void cleanup() {
        if (serverThread != null) {
            serverThread.interrupt();
        }
        // clean up test files
        new File("users.dat").delete();
        new File("reservations.dat").delete();
    }

    // this one tests the connecigton of the socket and port 

    @Test
    void testServerConnection() throws IOException {
        Socket socket = new Socket(TEST_HOST, TEST_PORT);
        assertTrue(socket.isConnected());
        socket.close();
    }

    // this test tests creating the user

    @Test
    void testCreateUser() throws IOException {
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send create user command
            out.println("1");

            // Get username prompt
            String prompt = in.readLine();
            assertTrue(prompt.toLowerCase().contains("username"));

            // Send username
            out.println("testuser" + System.currentTimeMillis());

            // Get password prompt
            prompt = in.readLine();
            assertTrue(prompt.toLowerCase().contains("password"));

            // Send password
            out.println("testpass");

            // Check success message
            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("success"));
        }
    }

    // this test tests the login

    @Test
    void testLogin() throws IOException {
        String username = "loginuser" + System.currentTimeMillis();

        //first create a user
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("1");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("password123");
            in.readLine();
        }

        //test login
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("3");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("password123");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("success"));
        }
    }

    //this tests log in faliure

    @Test
    void testLoginFailure() throws IOException {
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("3");
            in.readLine();
            out.println("nonexistent");
            in.readLine();
            out.println("wrongpass");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("fail") ||
                    result.toLowerCase().contains("incorrect"));
        }
    }

    //this tests trying to make a reservation with no login 

    @Test
    void testReservationWithoutLogin() throws IOException {
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("4");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("not logged in") ||
                    result.toLowerCase().contains("log in first"));
        }
    }

    //this tets making a reservation

    @Test
    void testCreateReservation() throws IOException {
        String username = "reserveuser" + System.currentTimeMillis();

        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            //create user
            out.println("1");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("pass123");
            in.readLine();

            //login
            out.println("3");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("pass123");
            in.readLine();

            //create reservation
            out.println("4");
            in.readLine(); // time prompt
            out.println("7:00PM");
            in.readLine(); // day prompt
            out.println("Monday");
            in.readLine(); // party size prompt
            out.println("4");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("success"));
        }
    }

    //this tests removing a user

    @Test
    void testRemoveUser() throws IOException {
        String username = "removeuser" + System.currentTimeMillis();

        // create user
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("1");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("pass123");
            in.readLine();
        }

        //remove user
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("2");
            in.readLine();
            out.println(username);
            in.readLine();
            out.println("pass123");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("success") ||
                    result.toLowerCase().contains("removed"));
        }
    }
    

    @Test
    void testExitCommand() throws IOException {
        try (Socket socket = new Socket(TEST_HOST, TEST_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("6");

            String result = in.readLine();
            assertTrue(result.toLowerCase().contains("exit") ||
                    result.toLowerCase().contains("goodbye"));
        }
    }
}
