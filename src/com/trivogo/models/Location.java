package com.trivogo.models;

import com.trivogo.dao.HotelDAO;
import java.util.ArrayList;
import java.util.List;


public class Location {

    private String locationName;
    private List<Hotel> hotelList;
    private int numStandardRooms;
    private int numDeluxeRooms;

    public Location(String name) {
        setLocationName(name);
        hotelList = HotelDAO.getHotelsByLocation(name);
        numStandardRooms = 0;
        numDeluxeRooms = 0;
        setNumRooms();
    }


    public void setLocationName(String name) {
        this.locationName = name;

    }

    private void setNumRooms() {
        for(int i = 0; i < hotelList.size(); i++) {
            Hotel hot = hotelList.get(i);
            numStandardRooms += hot.getTotalStandardRooms();
            numDeluxeRooms += hot.getTotalDeluxeRooms();
        }
    }

    public String getLocationName() {
        return this.locationName;
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }
    
    public int getNumStandardRooms() {
        return numStandardRooms;
    }

    public int getNumDeluxeRooms() {
        return numDeluxeRooms;
    }


}
