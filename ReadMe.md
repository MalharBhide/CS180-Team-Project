# Project README

---

## Instructions to Compile and Run

The User, Reservation, Seating, Database, Server, and Client classes can be compiled and run in **Vocareum**.  
**Note:** Test cases should be run in IntelliJ because there are issues with JUnit packages on Vocareum.

**Steps:**

1. Compile all Java classes.
2. Run `Server.java` to start the server.
3. Run `Client.java` to connect to the server.
4. Interact with the menu in the client console to manage users and reservations.

---

## Submission Information

- **Malhar Bhide** – Created the GitHub repo, delegated responsibilities, debugged and proofread code, implemented User functionalities and the Main Menu, completed `UserInterface`. Created Server.java and Database.java.
- **Himangi Nepal** – Created `Reservation` class and interface, implemented cases 4 and 5 (create/cancel reservations), implemented persistence via `ObjectOutputStream` and `ObjectInputStream`, added thread-safety in `User` class. Assisted in creating and debgugging Client.java class and created README file. 
- **Laila Lone** – Designed and implemented `Seating` class and `SeatInterface`, created 4D array for managing seat reservations, integrated seating updates with the main program. Created Client.java class and ClientInterface.java.
- **Jiyara Bhatia** – Wrote JUnit test cases and main method tests for each class, documented all classes and functionality, drafted the README file.

---

## Class Descriptions

### User.java

### Functionality:
The `User` class is responsible for creating and managing individual user accounts. Each `User` object stores a username and password.
Additionally, the class maintains a static `ArrayList<User>` called `userList`, which keeps track of all created users in the system.
The class implements the `UserInterface` to ensure consistent behavior across all of the later developed user related classes.
Core features include user creation, data storage, and providing access to user credentials through getter methods.

### Testing:
The `User` class was tested using **JUnit** to verify that users are correctly created, added to the user list, and that getter 
methods return the expected values. Unit tests also ensured that duplicate users are not unintentionally added to the shared list. 
Manual console tests were performed to confirm the creation of users and correct display of stored data.

### Relationship to Other Classes:
The `User` class implements the `UserInterface`, providing a standard structure for user operations. 
The `Main` class may also create and manipulate `User` objects based on user input.

---

## Seating.java

### Functionality:
The `Seating` class keeps track of all seat availability across different days and time slots. 
It uses a 4D boolean array to represent seats for 7 days and 13 time slots, each with a 10x10 grid. 
The class lets you check if a seat is open, reserve a seat, and display the seating chart for a given day and time.

### Testing:
This class was tested using **JUnit** and manual checks. Tests made sure that reserving a seat updates correctly, 
invalid inputs (like wrong rows or times) are handled, and the seating display works as expected.
Please run the testcases in IntelliJ as they do not work in base vocareum.

### Relationship to Other Classes:
`Seating` implements the `SeatInterface` and is used by the `Reservation` class to manage seat bookings. 
Other classes may access it to view or modify seat availability.
---

## Reservation.java

### Functionality:
The `Reservation` class handles booking and canceling seat reservations for specific days and times. 
It stores details like the day, time, and party size, and connects with the `Seating` class to check and update seat availability. 
It ensures that users can only reserve open seats and prevents double bookings.

### Testing:
The class was tested using **JUnit** to confirm that reservations can be created, updated, and canceled correctly. 
Tests also verified that seat availability updates properly in coordination with the `Seating` class.

### Relationship to Other Classes:
`Reservation` works closely with `Seating` to manage seat status and may interact with the User class to link 
bookings to specific users. It relies on Seating for validation and seat control logic.

---

### Database.java

### Functionality:
Centralized storage for all `User` and `Reservation` objects. Implements `DatabaseInterface`.  
Provides thread-safe methods to add/remove users and reservations, save/load data from files, and retrieve reservations for a specific user.

### Testing:
Tested manually to ensure correct persistence and retrieval of user and reservation data. Synchronized methods ensure thread-safety in multi-client scenarios.

### Relationship to Other Classes:
`Server` uses `Database` to handle all user and reservation operations instead of accessing `User` static lists directly.

---

### Server.java

### Functionality:
Runs a multi-threaded server to handle multiple clients simultaneously. Receives client requests for:  

1. Create User  
2. Remove User  
3. Login  
4. Create Reservation  
5. Cancel Reservation  
6. Exit  
7. View Reservations 

Option 7 allows users to view all their current reservations.

### Testing:
Manual testing with multiple clients ensured proper synchronization and correct handling of concurrent requests.

### Relationship:
Communicates with `Client` over sockets and interacts with `Database`, `U

---

### Client.java

### Functionality:
Command-line client interface to connect to the server. Provides a menu for users to perform actions remotely:

1. Create User Account  
2. Remove User Account  
3. Login  
4. Create Reservation  
5. Cancel Reservation  
6. Exit  
7. View My Reservations

### New Feature:
Allows logged-in users to retrieve a list of their reservations from the server and display them in the console.

### Testing:
Tested manually to confirm proper communication with the server, correct menu navigation, validation of user input, and receipt of accurate reservation data.

### Relationship to Other Classes:
Uses network I/O to communicate with `Server`. Sends commands and receives responses related to user and reservation management. Interacts with `Database` indirectly via `Server` to fetch reservations.
