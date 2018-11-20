
package com.trivogo.models;

import java.util.*;

public class Hotel {
    private int id;
    private String name, location;
    private HashMap<HotelRoom, Integer> totalRooms = new HashMap<>();
    private HotelRoom stdRoom, dexRoom;
    private float rating;

    public Hotel(int id, String name, String location, int numDexRooms, int numStdRooms,
                 String dexRoomAmenities, float dexRoomRate,
                 String stdRoomAmenities, float stdRoomRate,
                 float rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        if (numStdRooms > 0) {
            stdRoom = new HotelRoom("standard", stdRoomAmenities, (int) stdRoomRate);
            totalRooms.put(stdRoom, numStdRooms);
        }
        if (numDexRooms > 0) {
            dexRoom = new HotelRoom("deluxe", dexRoomAmenities, (int) dexRoomRate);
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
