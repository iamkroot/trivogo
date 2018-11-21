package com.trivogo.dao;

import com.trivogo.models.*;
import com.trivogo.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookingDAO {
    private static Connection conn = DBConn.getConn();
    public static int addBooking(Booking booking) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bookings (hotelID, user, roomType, numRooms, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, booking.getHotel().getId());
            ps.setString(2, booking.getUser().getUsername());
            ps.setString(3, booking.getRoom().getType());
            ps.setInt(4, booking.getNumOfRooms());
            ps.setString(5, DateUtil.convertToDBFormat(booking.getCheckInDate()));
            ps.setString(6, DateUtil.convertToDBFormat(booking.getCheckOutDate()));
            ps.setString(7, booking.getStatus());
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement("SELECT id FROM bookings ORDER BY id DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;
    }

    public static void updateBooking(int bookingID, Date checkInDate, Date checkOutDate) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET checkInDate = ?, checkOutDate = ? WHERE id = ?");
            ps.setString(1, DateUtil.convertToDBFormat(checkInDate));
            ps.setString(2, DateUtil.convertToDBFormat(checkOutDate));
            ps.setInt(3, bookingID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateStatus(int bookingID, String status) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET status = ? WHERE id = ?");
            ps.setString(1, status);
            ps.setInt(2, bookingID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addReview(int bookingID, Review review) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET reviewID = ? WHERE id = ?");
            ps.setInt(1, review.getId());
            ps.setInt(2, bookingID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cancelBooking(int bookingID) {
        updateStatus(bookingID, "CANCELLED");
    }

    public static void confirmWaitlistedBooking(int bookingID) {
        updateStatus(bookingID, "WAITLIST CONFIRMED");
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
            booking.setReview(ReviewDAO.getReviewByID(rs.getInt("reviewID")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public static Booking getBookingByID(int id) {
        Booking booking = null;
        try {
            PreparedStatement getBookings = conn.prepareStatement("SELECT * FROM bookings WHERE id = ?");
            getBookings.setInt(1, id);
            ResultSet rs = getBookings.executeQuery();
            while (rs.next()) {
                booking = fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public static List<Booking> getWaitlistPendingBookings(Hotel hotel, HotelRoom hotelRoom) {
        List<Booking> bookingsList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bookings WHERE status = 'WAITLIST PENDING'" +
                    "AND hotelID = ? AND roomType = ?");
            ps.setInt(1, hotel.getId());
            ps.setString(2, hotelRoom.getType());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                bookingsList.add(fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsList;
    }

    public static List<Booking> getHotelBookings(Hotel hotel) {
        List<Booking> bookings = new ArrayList<>();
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
    public static int getNumAvailableRooms(Booking booking) {
        return getNumAvailableRooms(booking.getCheckInDate(), booking.getCheckOutDate(), booking.getHotel(), booking.getRoom());
    }
    public static int getNumAvailableRooms(Date checkInDate, Date checkOutDate, Hotel hotel, HotelRoom hotelRoom) {
        int numBookedRooms = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT numRooms FROM bookings WHERE checkInDate < ? AND checkOutDate > ?" +
                    "AND hotelID = ? AND roomType = ? AND instr(status, 'CONFIRMED')");
            ps.setString(1, DateUtil.convertToDBFormat(checkOutDate));
            ps.setString(2, DateUtil.convertToDBFormat(checkInDate));
            ps.setInt(3, hotel.getId());
            ps.setString(4, hotelRoom.getType());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numBookedRooms += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int totalRooms;
        if(hotelRoom.getType().equals("Deluxe")){
            totalRooms = hotel.getTotalDeluxeRooms();
        }
        else
            totalRooms = hotel.getTotalStandardRooms();
        return totalRooms - numBookedRooms;
    }
}
