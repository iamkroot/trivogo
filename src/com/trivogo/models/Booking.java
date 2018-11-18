package com.trivogo.models;
import java.text.*;

public class Booking {
    private Hotel hotel;
    private User user;
    private HotelRoom room;
    private java.util.Date checkInDate; 
    private java.util.Date checkOutDate;
    private int numOfRooms;
    
    public Booking(Hotel hotel, User user, HotelRoom room, int numRooms, java.util.Date inDate, java.util.Date outDate) {
        setHotel(hotel);
        setUser(user);
        setRoomType(room);
        setNumOfRooms(numRooms);
        setCheckInDate(inDate);
        setCheckOutDate(outDate);
    }
    
    public void setHotel(Hotel hotel){
        this.hotel = hotel;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setRoomType(HotelRoom room){
        this.room = room;
    }
    public void setNumOfRooms(int nr){
        this.numOfRooms = nr;
    }
    public void setCheckInDate(java.util.Date inDate){
        this.checkInDate = inDate;
    }
    public void setCheckOutDate(java.util.Date outDate){
        this.checkOutDate = outDate;
    }
   
    
    public Hotel getHotel() {
        return this.hotel;
    }
    
    
    public User getUser() {
        return this.user;
    }
    
    
    public HotelRoom getRoomType() {
        return this.room;
    }
    
    
    public int getNumOfRooms() {
        return this.numOfRooms;
    }
    
    
    public java.util.Date getCheckInDate() {
        return this.checkInDate;
    }
    
    public java.util.Date getCheckOutDate() {
        return this.checkOutDate;
    }
    
}