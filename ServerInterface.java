import java.net.Socket;
/**
 *  Server interface defining methods for server operations.
 * @author Malhar Bhide
 * @version November 24th, 2025
 */
public interface ServerInterface {
    void run();
    void handleClient(Socket socket);
}