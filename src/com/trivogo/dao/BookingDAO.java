package com.trivogo.dao;

import com.trivogo.models.Booking;
import com.trivogo.models.Hotel;
import com.trivogo.models.HotelRoom;
import com.trivogo.models.User;
import com.trivogo.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingDAO {
    public static int newBooking(Booking booking) {
        Connection conn = DBConn.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bookings (hotelID, user, roomType, numRooms, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, booking.getHotel().getId());
            ps.setString(2, booking.getUser().getUsername());
            ps.setString(3, booking.getRoom().getType());
            ps.setInt(4, booking.getNumOfRooms());
            ps.setDate(5, (Date) booking.getCheckInDate());
            ps.setDate(6, (Date) booking.getCheckOutDate());
            ps.setString(7, booking.getStatus());
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
}
