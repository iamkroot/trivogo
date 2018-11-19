package com.trivogo.dao;

import com.trivogo.models.*;
import com.trivogo.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingDAO {
    public static int newBooking(Booking booking) {
        Connection conn = DBConn.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bookings (hotelID, user, roomType, numRooms, checkInDate, checkOutDate, status, location) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, booking.getHotel().getId());
            ps.setString(2, booking.getUser().getUsername());
            ps.setString(3, booking.getRoom().getType());
            ps.setInt(4, booking.getNumOfRooms());
            ps.setString(5, DateUtil.convertToDBFormat(booking.getCheckInDate()));
            ps.setString(6, DateUtil.convertToDBFormat(booking.getCheckOutDate()));
            ps.setString(7, booking.getStatus());
            ps.setString(8, booking.getHotel().getLocation());
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement("SELECT * FROM bookings ORDER BY id DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;
    }

    private static Booking fromResultSet(ResultSet rs) {
        Booking booking = null;
        try {
            Hotel hotel = HotelDAO.getHotelByID(rs.getInt("hotelID"));
            HotelRoom hotelRoom;
            if (rs.getString("roomType").equals("deluxe"))
                hotelRoom = hotel.getDexRoom();
            else
                hotelRoom = hotel.getStdRoom();
            booking = new Booking(hotel, UserDAO.getUser(rs.getString("user")),
                    hotelRoom, rs.getInt("numRooms"),
                    DateUtil.readFromDB(rs.getString("checkInDate")),
                    DateUtil.readFromDB(rs.getString("checkOutDate")), rs.getString("status"));
            booking.setBookingID(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public static List<Booking> getHotelBookings(Hotel hotel) {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DBConn.getConn();
        try {
            PreparedStatement getBookings = conn.prepareStatement("SELECT * FROM bookings WHERE hotelID = ?");
            getBookings.setInt(1, hotel.getId());
            ResultSet rs = getBookings.executeQuery();
            while (rs.next()) {
                bookings.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static List<Booking> getUserBookings(User user) {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DBConn.getConn();
        try {
            PreparedStatement getBookings = conn.prepareStatement("SELECT * FROM bookings WHERE user = ?");
            getBookings.setString(1, user.getUsername());
            ResultSet rs = getBookings.executeQuery();
            while (rs.next()) {
                bookings.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static int getNumAvailableRooms(SearchParameters params) {
        String checkInDate = DateUtil.convertToDBFormat(params.getCheckInDate());
        String checkOutDate = DateUtil.convertToDBFormat(params.getCheckOutDate());
        Connection conn = DBConn.getConn();
        int numBookedRooms = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT numRooms FROM bookings WHERE checkInDate < ? AND checkOutDate > ?" +
                    "AND location = ? AND status = 'CONFIRMED'");
            ps.setString(1, checkOutDate);
            ps.setString(2, checkInDate);
            ps.setString(3, params.getLocation().getLocationName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numBookedRooms += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Location location = params.getLocation();
        int totalRooms = location.getNumDeluxeRooms() + location.getNumStandardRooms();
        return totalRooms - numBookedRooms;
    }
}
