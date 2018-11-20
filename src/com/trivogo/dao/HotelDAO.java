package com.trivogo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.trivogo.models.Hotel;

public class HotelDAO {
    private static Connection conn = DBConn.getConn();
    public static Hotel getHotelByID(int id) {
        Hotel hotel = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from hotels where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hotel = new Hotel(id, rs.getString("name"), rs.getString("location"),
                        rs.getInt("numDexRooms"), rs.getInt("numStdRooms"),
                        rs.getString("dexRoomAmenities"), rs.getFloat("dexRoomRate"),
                        rs.getString("stdRoomAmenities"), rs.getFloat("stdRoomRate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    public static List<String> getAllLocations() {
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
