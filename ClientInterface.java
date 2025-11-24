public interface ClientInterface {
    void start();
    boolean connectToServer();
    void createUser();
    void login(); 
    void createReservation();
    void cancelReservation();
    void closeConnection();
}