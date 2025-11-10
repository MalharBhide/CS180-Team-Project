# Project README

---

## Instructions to Compile and Run
Provide clear steps on how to compile and run your project.

## Submission Information 
* Malhar  – 
* Himangi – 
* Laila  – 
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


