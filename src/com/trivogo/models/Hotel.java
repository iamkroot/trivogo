
package com.trivogo.models;

class Hotel {
    private String name, location;
    private int numOfDeluxeRooms, numOfStandardRooms;
    private DeluxeRoom[] DexRooms;
    private StandardRoom[] StdRooms;

    public Hotel(String name, String location, int numDexRooms, int numStdRooms,
                 String DexRoomAmenities, int DexRoomRate,
                 String StdRoomAmenities, int StdRoomRate) {
        this.name = name;
        this.location = location;

        DexRooms = new DeluxeRoom[numDexRooms];
        for (int i = 0; i < numDexRooms; i++) {
            DexRooms[i] = new DeluxeRoom(DexRoomAmenities, DexRoomRate);
        }

        StdRooms = new StandardRoom[numStdRooms];
        for (int i = 0; i < numStdRooms; i++) {
            StdRooms[i] = new StandardRoom(StdRoomAmenities, StdRoomRate);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }
}
