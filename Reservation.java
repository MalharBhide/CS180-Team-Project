/*
* CS 180 Team Project 
* 
*
*
* @author Himangi Nepal
* @version 1.0
*/

public class Reservation{

private int time; 
private String day;
private int partySize;

public Reservation(int time, String day, int partySize){

this.time = time; 
this.day = day; 
this.partySize = partySize; 

}

// Ability to select a day for the reservation
//Ability to select a time for the reservation
//Ability to view all open seats at the given time
//Ability to book varying party sizes
//View pricing - if applicable
//Cancel reservations

public int getTime(){

     return time;

}

public String getDay(){

    return day; 

}

public int getPartySize(int partySize){

    return partySize; 

}

public int setTime(int time){

    this.time= time;

}

public void setDay(String day){

    this.day = day; 
    
}

public void setPartySize(){

    this.partySize = partySize; 

}

public void cancelReservation(){


     //check if the reservation is booked, if true then allow them to cancel,
     //else return that the reservation hasn't been booked 

    

}


public void bookReservation(){

//select a time, day and then 
//access the array of available seats 
//choose party size
//if seats are available, reserve seats, else, return message that seats are not available

}




}