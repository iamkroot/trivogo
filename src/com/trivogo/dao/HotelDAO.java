package com.trivogo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.trivogo.models.Hotel;

public class HotelDAO {
    public static List<String> getAllLocations() {
        Connection conn = DBConn.getConn();
        List<String> locations = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT location FROM hotels");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                locations.add(rs.getString(1));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static List<Hotel> getHotelsByLocation(String location) {
        Connection conn = DBConn.getConn();
        List<Hotel> hotels = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM hotels WHERE location = ?");
            ps.setString(1, location);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getInt("numDexRooms"), rs.getInt("numStdRooms"), rs.getString("dexRoomAmenities"), rs.getInt("dexRoomRate"), rs.getString("stdRoomAmenities"), rs.getInt("stdRoomRate"));
                hotels.add(hotel);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}
