package com.trivogo.models;

import java.util.Date;

public class SearchParameters {

    private Location location;
    private java.util.Date checkInDate;
    private java.util.Date checkOutDate;
    private int numPeople;
    private int numRooms;

    public SearchParameters(String loc, java.util.Date in, java.util.Date out, int numPeople, int numRooms) {
        setLocation(loc);
        setCheckInDate(in);
        setCheckOutDate(out);
        setNumPeople(numPeople);
        setNumRooms(numRooms);
    }


    public Location getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = new Location(location);
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

}
