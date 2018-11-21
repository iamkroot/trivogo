
package com.trivogo.models;

import java.util.*;

public class Hotel {
    private int id;
    private String name, location;
    private HashMap<HotelRoom, Integer> totalRooms = new HashMap<>();
    private HotelRoom stdRoom, dexRoom;
    private int rating;

    public Hotel(int id, String name, String location, int numDexRooms, int numStdRooms,
                 String dexRoomAmenities, float dexRoomRate,
                 String stdRoomAmenities, float stdRoomRate,
                 int rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        if (numStdRooms > 0) {
            stdRoom = new HotelRoom("Standard", stdRoomAmenities, (int) stdRoomRate);
            totalRooms.put(stdRoom, numStdRooms);
        }
        if (numDexRooms > 0) {
            dexRoom = new HotelRoom("Deluxe", dexRoomAmenities, (int) dexRoomRate);
            totalRooms.put(dexRoom, numDexRooms);
        }
        setRating(rating);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public int getTotalStandardRooms() {
        return totalRooms.getOrDefault(stdRoom, 0);
    }

    public int getTotalDeluxeRooms() {
        return totalRooms.getOrDefault(dexRoom, 0);
    }

    public HotelRoom getStdRoom() {
        return stdRoom;
    }

    public HotelRoom getDexRoom() {
        return dexRoom;
    }

    public HotelRoom getRoomInfo(String type) {
        if(type.equals("Deluxe"))
            return dexRoom;
        else
            return stdRoom;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
