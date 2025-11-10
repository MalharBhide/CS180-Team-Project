# Project README

---

## Instructions to Compile and Run
To compile and run the project, open the project folder in IntelliJ or any Java IDE and ensure that all .java files are in the same package or directory. Build the project using the “Build Project” option to compile all classes, then run the Main.java file to start the program. To test the functionality, open the test folder and run all JUnit test classes, such as UserTest, SeatingTest, and ReservationTest, to verify that each part of the program works as expected.

## Submission Information 
* Malhar Bhide – Created the GitHub, Played a key role in delegating responsibilities, Debugged and Proofreaded code,vWorked on User functionalities and implementing the “Main Menu”, Completed UserInterface.
* Himangi Nepal – Created Reservation class and Reservation interface. Created Cases 4 and 5 which prompt the user to create a reservation and cancel a reservation. Wrote methods using objectOutputStream to write Reservation and User Objects to the file, as well as Load methods which deserialize these objects in order to be loaded in every time the program is called on, therefore ensuring data persistence. 
* Laila Lone – designed and implemented seating class and SeatInterface. Worked on 4D array that allows users to make reservations at different days and times. I also implemented my methods with the main program so that the seating chart updates whenever a reservation is created or canceled.
* Jiyara Bhatia – I wrote all the JUnit test cases for each class to ensure that every part of the program worked correctly and met the project requirements. I also created the outline document in Google Docs to help organize our team’s workflow and structure the project early on. In addition, I was responsible for documenting everything in the README file, clearly explaining each class, its functionality, and how to run and test the program.


## Class Desciptions 

## User.java

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



## Seating.java

### Functionality:
The `Seating` class keeps track of all seat availability across different days and time slots. 
It uses a 4D boolean array to represent seats for 7 days and 13 time slots, each with a 10x10 grid. 
The class lets you check if a seat is open, reserve a seat, and display the seating chart for a given day and time.

### Testing:
This class was tested using **JUnit** and manual checks. Tests made sure that reserving a seat updates correctly, 
invalid inputs (like wrong rows or times) are handled, and the seating display works as expected.

### Relationship to Other Classes:
`Seating` implements the `SeatInterface` and is used by the `Reservation` class to manage seat bookings. 
Other classes may access it to view or modify seat availability.


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


