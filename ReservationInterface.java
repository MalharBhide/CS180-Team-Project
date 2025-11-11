/**
 * CS 180 Team Project
 * ReservationInterface
 *
 *
 *
 * @author Himangi Nepal
 * @version Nov 10th, 2025
 */
public interface ReservationInterface {
    public String getTime();
    public String getDay();
    public int getPartySize();
    public void setTime(String time);
    public void setDay(String day);
    public void setPartySize(int partySize);
    public void viewOpenSeats();
    public void bookReservation();
    public void cancelReservation();

}