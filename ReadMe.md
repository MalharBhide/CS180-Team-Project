# Project README

---

## Instructions to Compile and Run
Provide clear steps on how to compile and run your project.

## Submission Information 
* Malhar Bhide – Created the GitHub, Worked on User functionalities and implementing the “Main Menu”, Completed UserInterface.
* Himangi Nepal – Created Reservation class and Reservation interface. Created Cases 4 and 5 which prompt the user to create a
* reservation and cancel a reservation. Wrote methods using objectOutputStream to write Reservation and User Objects to the file,
* as well as Load methods which deserialize these objects in order to be loaded in every time the program is called on, therefore ensuring
* data persistence. 
* Laila Lone – designed and implemented seating class and SeatInterface. Worked on 4D array that allows users to make reservations at
* different days and times. I also implemented my methods with the main program so that the seating chart updates whenever a reservation
* is created or canceled.
* Jiyara Bhatia – 


## Class Desciptions 

User.java

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


