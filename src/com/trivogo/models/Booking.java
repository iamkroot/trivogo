package com.trivogo.models;

import java.util.Date;

public class Booking {
    private Hotel hotel;
    private User user;
    private HotelRoom room;
    private java.util.Date checkInDate; 
    private java.util.Date checkOutDate;
    private int numOfRooms;
    private int bookingID;
    private String status;
    private Review review;
    private int payableAmount;
    public Booking(Hotel hotel, User user, HotelRoom room, SearchParameters params, String status) {
        this(hotel, user, room, params.getNumRooms(), params.getCheckInDate(), params.getCheckOutDate(), status);
    }

    public Booking(Booking booking) {
        this(booking.getHotel(), booking.getUser(), booking.getRoom(), booking.getNumOfRooms(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getStatus());
        this.payableAmount = booking.getPayableAmount();
        this.bookingID = booking.getBookingID();
    }

    public Booking(Hotel hotel, User user, HotelRoom room, int numRooms, java.util.Date inDate, java.util.Date outDate, String stat) {
        setHotel(hotel);
        setUser(user);
        setRoom(room);
        setNumOfRooms(numRooms);
        setCheckInDate(inDate);
        setCheckOutDate(outDate);
        setStatus(stat);
        payableAmount = 0;
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
    public void setReview(Review review) {
        this.review = review;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
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

    public int getPayableAmount() {
        return this.payableAmount;
    }

    public Review getReview() {
        return review;
    }

    public boolean isLapsed() {
        return checkInDate.before(new Date()) && (status.contains("CONFIRMED") || status.contains("WAITLIST"));
    }
}