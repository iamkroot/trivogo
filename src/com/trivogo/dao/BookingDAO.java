package com.trivogo.dao;

import com.trivogo.models.Booking;
import com.trivogo.models.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public static void newBooking(Booking booking) {
        Connection conn = DBConn.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bookings (hotelID, user, roomType, numRooms, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, booking.getHotel().getId());
            ps.setString(2, booking.getUser().getUsername());
            ps.setString(3, booking.getRoom().getType());
            ps.setInt(4, booking.getNumOfRooms());
            ps.setDate(5, (Date) booking.getCheckInDate());
            ps.setDate(6, (Date) booking.getCheckOutDate());
//            ps.setString(7, booking.getStatus());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static List<Booking> getUserBookings(User user) {
        List<Booking> bookings = new ArrayList<>();
        return bookings;
    }
}
