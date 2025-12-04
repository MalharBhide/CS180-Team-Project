import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*; 

/**
* CS 180 team project 1
* Client class for Restaurant Reservation System
*
* @author Laila
* @version November 2025
*/
public class Client implements ClientInterface {
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
       JOptionPane.showMessageDialog(
      	     null,
	     "Restaurant Reservation System",
	     "Welcome",
      	     JOptionPane.INFORMATION_MESSAGE
	);


       if (!connectToServer()) {
	    JOptionPane.showMessageDialog(
	    	 Null,
	    	 "Failed to connect to server.",
	   	 "Connection Error",
	   	 JOptionPane.ERROR_MESSAGE
	    );
           return;
       }


       // Main menu
       while (true) {
           String choice =  showMainMenu();


	    if (choice == null) {
		int confirm = JOptionPane.showConfirmDialog (
			null, 
			"Are you sure you want to exit?",
			"Confirm Exit",
			JOptionPane.YES_NO_OPTION
		);


	   if (confirm == JOptionPane.YES_OPTION) { 
		JOptionPane.showMessageDialog(null, "Goodbye!");
		closeConnection();
		return;
	   }
		continue;
          
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
                   JOptionPane.showMessageDialog(null, "Goodbye!");
		      closeConnection();
		      return;
               Default:
                  JOptionPane.showMessageDialog(null,"Invalid option. Please try again.");
           }
       }
   }


   public boolean connectToServer() {
       try {
           System.out.print("Enter server host (localhost): ");
           String host = JOptionPane.showInputDialog(
		null, 
             "Enter server host (localhost):",
             “Server Connection”,
             JOptionPane.QUESTION_MESSAGE
       );


           if (host == null) {
		return false;
	    }


	    if (host.trim().isEmpty()) host = "localhost";


	    String portStr = JOptionPane.showInputDialog( 
             null, 
             "Enter server port:", 
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


   private String showMainMenu() {


       String[] options = {
		"Create User Account",
		"Remove User Account", 
		"Login",
		"Create Reservation",
		"Cancel Reservation", 
		"Exit"
       };
	int choice = JOptionPane.showOptionDialog(
null, 
"Please select and option:",
"Main Menu",
JOptionPane.DEFAULT_OPTION, 
JOptionPane.QUESTION_MESSAGE,
null,
options,
options[0]
);


	return (choice == -1) ? null: String.valueOf(choice + 1);
   }


   public void createUser() {
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


   public void login() {
       try {
           out.println("3"); // logging in
          
           String prompt = in.readLine();
           String password = JOptionPane.showInputDialog(null, prompt, "Login", JOptionPane.QUESTION_MESSAGE);
           out.println(username);
          
           prompt = in.readLine();
           String password = JOptionPane.showInputDialog(null, prompt, "Login", JOptionPane.QUESTION_MESSAGE);
           out.println(password);
          
           String result = in.readLine();
           
	    JOptionPane.showMessageDialog(null, result, "Login Status", JOPtionPane.INFORMATION_MESSAGE);
          
           if (result.contains("successful")) {
               loggedIn = true;
               currentUser = username;
           }
          
       } catch (IOException e) {
           JOptionPane.showMessageDIalog(null, "Error during login: " + e.getMessage(),
	    "Login Error", JOptionPane.ERROR_MESSAGE);
       }
   }


   public void createReservation() {
       if (!loggedIn) {
           JOptionPane.showMessageDialog(null , "Please login first!" , "Error" , JOptionPane.ERROR_MESSAGE );
           return;
       }
      
       try {
           out.println("4"); // creating reservation
          
           String prompt = in.readLine();
           String time = JOptionPane.showInputDialog(null , prompt);
           out.println(time);
          
           prompt = in.readLine();
           String day = JOptionPane.showInputDialog(null , prompt); 
           out.println(day);
          
           prompt = in.readLine();
           String partySize = JOptionPane.showInputDialog(null , prompt);
           out.println(partySize);
          
           String result = in.readLine();
           JOptionPane.showMessageDialog(null , result);
          
       } catch (IOException e) {
           JOptionPane.showMessageDialog(null , "Error creating reservation: " + e.getMessage());
       }
   }


   public void cancelReservation() {
       if (!loggedIn) {
           JOptionPane.showMessageDialog(null , "Please login first!");
           return;
       }
      
       try {
           out.println("5"); // canceling reservation
          
           String prompt = in.readLine();
           String day = JOptionPane.showInputDialog(null , prompt);
           out.println(day);
          
           prompt = in.readLine();
           String time = JOptionPane.showInputDialog(null , prompt);
           out.println(time);
          
           String result = in.readLine();
           JOptionPane.showMessageDialog(null , result);
          
       } catch (IOException e) {
           JOptionPane.showMessageDialog(null , "Error canceling reservation: " + e.getMessage());
       }
   }


   public void closeConnection() {
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
           out.println("1"); // tell server we want to create user


           out.println(usernameField.getText());
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


           out.println(usernameField.getText());
           out.println(new String(passwordField.getPassword()));


           String result = in.readLine();
           JOptionPane.showMessageDialog(null, result);


       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
       }
   }
}
