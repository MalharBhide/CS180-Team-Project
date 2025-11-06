/*
 * CS 180 Team Project
 * ReservationInterface
 *
 *
 *
 * @author Himangi Nepal
 * @version 1.0
 */

public interface ReservationInterface {
    
        public int getTime();
        
        public String getDay();

        public int getPartySize();
        
        public void setTime(int time);
        
        public void setDay(String day);

        public void setPartySize(int partySize);

        public void viewOpenSeats();

        public void bookReservation();

        public void cancelReservation();
        
}