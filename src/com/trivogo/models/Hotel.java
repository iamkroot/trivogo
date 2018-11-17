
package com.trivogo.models;

import java.util.*;

public class Hotel {
    private String name, location;
    private HashMap<HotelRoom, Integer> totalRooms = new HashMap<>();
    private DeluxeRoom dexRoom;
    private StandardRoom stdRoom;

    public Hotel(String name, String location, int numDexRooms, int numStdRooms,
                 String dexRoomAmenities, int dexRoomRate,
                 String stdRoomAmenities, int stdRoomRate) {
        this.name = name;
        this.location = location;
        if (numStdRooms > 0) {
            stdRoom = new StandardRoom(stdRoomAmenities, stdRoomRate);
            totalRooms.put(stdRoom, numStdRooms);
        }
        if (numDexRooms > 0) {
            dexRoom = new DeluxeRoom(dexRoomAmenities, dexRoomRate);
            totalRooms.put(dexRoom, numDexRooms);
        }
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

    public StandardRoom getStdRoom() {
        return stdRoom;
    }

    public DeluxeRoom getDexRoom() {
        return dexRoom;
    }
}
