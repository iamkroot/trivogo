package com.trivogo.models;
import java.text.*;

public class Booking {
    private Hotel hotel;
    private User user;
    private HotelRoom room;
    private java.util.Date checkInDate; 
    private java.util.Date checkOutDate;
    private int numOfRooms;
    private int bookingID;
    private String status;

    public Booking(Hotel hotel, User user, HotelRoom room, SearchParameters params, String status) {
        this(hotel, user, room, params.getNumRooms(), params.getCheckInDate(), params.getCheckOutDate(), status);
    }
    
    public Booking(Hotel hotel, User user, HotelRoom room, int numRooms, java.util.Date inDate, java.util.Date outDate, String stat) {
        setHotel(hotel);
        setUser(user);
        setRoom(room);
        setNumOfRooms(numRooms);
        setCheckInDate(inDate);
        setCheckOutDate(outDate);
        setStatus(stat);
    }
    
    public void setHotel(Hotel hotel){
        this.hotel = hotel;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setRoom(HotelRoom room){
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
    public void setStatus(String s) {
        this.status = s;
    }
    public void setBookingID(int bID) {
        this.bookingID = bID;
    }
    
    public Hotel getHotel() {
        return this.hotel;
    }
    
    public User getUser() {
        return this.user;
    }

    public HotelRoom getRoom() {
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
    
    public String getStatus() {
        return this.status;
    }
    
    public int getBookingID() {
        return this.bookingID;
    }
}