/** 
*
* Interface for the client class
*@author Laila 
*@version 
*
*/
public interface ClientInterface {
    void start();
    boolean connectToServer();
    void createUser();
    void login(); 
    void createReservation();
    void cancelReservation();
    void closeConnection();
}