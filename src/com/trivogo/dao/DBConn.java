package com.trivogo.dao;

import com.trivogo.models.*;
import com.trivogo.utils.DateUtil;
import com.trivogo.utils.Hasher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DBConn {
    private static Connection conn = null;

    private static void ensureDriverExists() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite Driver error.");
            System.exit(1);
        }
    }

    static Connection getConn() {
        ensureDriverExists();
        try {
            if (conn != null && !conn.isClosed())
                return conn;
            conn = DriverManager.getConnection("jdbc:sqlite:trivogo.db");
        } catch (SQLException e) {
            System.err.println("Error while trying to connect to trivogo.db");
            System.exit(1);
        }
        return conn;
    }

    public static void populateDB() {
        conn = getConn();
//        populateHotels();
        populateBookings();
//        populateUsers();
    }

    private static void createTables() {
        String createUserTable = "CREATE TABLE users (" +
                "username TEXT PRIMARY KEY," +
                "fullName TEXT," +
                "email TEXT," +
                "address TEXT," +
                "dob TEXT," +
                "passwordHash TEXT" +
                ");";
        String createHotelTable = "CREATE TABLE hotels (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "location TEXT," +
                "numDexRooms INTEGER," +
                "numStdRooms INTEGER," +
                "dexRoomAmenities TEXT," +
                "stdRoomAmenities TEXT," +
                "overallRating REAL);";
        try {
            PreparedStatement ps = conn.prepareStatement(createUserTable);
            System.out.println(ps.execute());
            ps = conn.prepareStatement(createHotelTable);
            System.out.println(ps.execute());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void populateUsers() {
        User[] dummyUsers = {
                new User("vg", "Van Gogh", "omni@example.com", "There", "01/04/1725", Hasher.hash("rich")),
                new User("pp", "Pablo Picasso", "pavlo@example.com", "Yo Mama", "28/11/1825", Hasher.hash("rich")),
                new User("dv", "Da Vinci", "leo@example.com", "Moan a Lisa", "15/09/1546", Hasher.hash("rich"))
        };
        for (User user : dummyUsers) {
            UserDAO.addUser(user);
        }
    }

    private static void populateHotels() {
        Object hotelData[][] = {
                {"Hilton", "Hyderabad", 200, 300, 2300f, 1500f, "Wifi, Breakfast, Club House", "Wifi"},
                {"Mercure Grand", "New Delhi", 100, 100, 5000f, 3100f, "Wifi, Breakfast, Gym", "Breakfast"},
                {"Taj", "Mumbai", 630, 190, 10000f, 2200f, "Wifi, Breakfast, Club, Gym, Taxi Service", "Wifi, Breakfast"},
                {"Leela Palace", "Cochin", 50, 210, 7500f, 4000f, "Breakfast, In room Pool, Spa, Taxi Service, Wifi, Club, Gym", "Breakfast, Pool, Spa, Wifi"},
                {"Novotel", "Hyderabad", 50, 210, 750f, 400f, "Breakfast, Taxi Service", "Breakfast"},
                {"Grand Lotus", "Hyderabad", 50, 200, 12000f, 8000f, "Breakfast Pool", "Pool"},
                {"Fort House Inn", "New Delhi", 5, 10, 3400f, 1200f, "Spa, Pool", "Spa"},
                {"MGM Grande", "Mumbai", 500, 750, 10000f, 75000f, "Poker Table, Conference Room, Gym", "Conference Room, Gym"},
                {"Grand Hilton", "New Delhi", 200, 400, 8000f, 4300f, "Premium View, Pool, Spa, Guided Tour", "Guided Tour, Pool, Spa"},
                {"Ramada", "Cochin", 12, 50, 12000f, 5700f, "Indoor pool, Spa, Massage Center, Breakfast", "Spa, Pool, Breakfast"},
                {"Vivanta", "Cochin", 80, 120, 18000f, 12000f, "Breakfast, Pool, Gym, Boat Ride, Spa", "Breakfast, Pool, Gym"},
                {"Ritz Carlton", "Mumbai", 100, 150, 23000f, 14500f, "Breakfast, Pool, Gym, Guided Tour, Theater", "Breakfast, Pool, Gym"},
                {"Leonia", "Hyderabad", 500, 1000, 7500f, 2850f, "Theater, Play Ground, Multiple Cuisines, Spa", "Theater, Play Ground"},
        };
        try {
            PreparedStatement ps = conn.prepareStatement("insert into hotels (name, location, numDexRooms, numStdRooms, dexRoomRate, stdRoomRate, dexRoomAmenities, stdRoomAmenities) values (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Object[] hotel : hotelData) {
                ps.setString(1, (String) hotel[0]);
                ps.setString(2, (String) hotel[1]);
                ps.setInt(3, (Integer) hotel[2]);
                ps.setInt(4, (Integer) hotel[3]);
                ps.setFloat(5, (Float) hotel[4]);
                ps.setFloat(6, (Float) hotel[5]);
                ps.setString(7, (String) hotel[6]);
                ps.setString(8, (String) hotel[7]);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void populateBookings() {
        Object[][] bookingData = {
                {1, "asdf", "deluxe", 5, "2018-11-19", "2018-11-23", "CONFIRMED"},
                {4, "dv", "standard", 1,  "2018-12-1", "2018-12-2", "CONFIRMED"},
                {9, "pp", "standard", 13, "2018-11-23", "2018-11-27", "CONFIRMED"},
                {7, "dv", "deluxe", 1, "2018-12-23", "2019-01-02", "CONFIRMED"},
                {5, "asdf", "deluxe", 10, "2018-11-28", "2018-12-03", "CONFIRMED"},
                {13, "pp", "deluxe", 3, "2018-12-12", "2018-12-15", "CONFIRMED"},
                {3, "1", "deluxe", 2, "2018-12-20", "2019-01-03", "CONFIRMED"},
                {10, "asdf", "deluxe", 11, "2018-12-15", "2019-01-05", "CONFIRMED"},
                {2, "dv", "standard", 56, "2018-12-13", "2018-12-19", "CONFIRMED"},
                {10, "pp", "standard", 1, "2018-12-23", "2018-12-28", "WAITLIST PENDING"},
                {2, "asdf", "standard", 1, "2018-12-01", "2018-12-02", "CONFIRMED"},
                {1, "1", "deluxe", 1, "2018-11-16", "2018-11-19", "CANCELLED"},
                {4, "asdf", "standard",45, "2018-11-25", "2018-12-15", "CONFIRMED"},
                {7, "1", "deluxe", 3, "2018-12-22", "2019-01-02", "CANCELLED"},
                {10, "1", "deluxe", 1, "2018-12-20", "2018-12-22", "CONFIRMED"},
                {6, "1", "deluxe", 5, "2018-11-25", "2018-11-30", "CONFIRMED"},
                {7, "dv", "deluxe", 2, "2018-12-23", "2019-01-02", "WAITLIST CONFIRMED"},
                {2, "1", "standard", 42, "2018-12-10", "2018-12-26", "CONFIRMED"},
                {9, "asdf", "standard", 14, "2018-12-13", "2018-12-16", "CANCELLED"},
                {8, "1", "standard", 250, "2018-12-15", "2018-12-27", "CONFIRMED"},
                {7, "1", "standard", 10, "2018-12-23", "2018-12-27", "CONFIRMED"},
                {6, "1", "deluxe", 45, "2018-12-11", "2018-12-20", "CONFIRMED"},
                {11, "1", "standard", 78, "2018-12-02", "2018-12-08", "CONFIRMED"},
                {12, "dv", "deluxe", 12, "2018-12-06", "2018-12-15", "CONFIRMED"}

                /*These intros should cause waitlist
                {2, "dv", "standard", 10, "2018-12-15", "2018-12-17", "<waitlist>"},
                {4, "pp", "deluxe", 7, "2018-12-01", "2018-11-04", "<waitlist>"},
                {7, "asdf", "deluxe", 3, "2018-12-24", "2018-12-26", "<waitlist>"},
                {7, "asdf", "standard", 1, "2018-12-24", "2018-12-26", "<waitlist>"},
                {10, "dv", "deluxe", 2, "2018-12-18", "2018-12-23", "<waitlist>"},
                {6, "1", "deluxe", 10, "2018-12-15", "2018-12-20", "<waitlist>"},
                {INTHOTELID, "asdf", "standard", INTNUMROOMS, "INDATE", "OUTDATE", "<waitlit>"}*/

        };
        for (int i = 0; i < bookingData.length; i++) {
            Object[] data = bookingData[i];
            Hotel hotel = HotelDAO.getHotelByID((Integer) data[0]);
            HotelRoom hr;
            if(data[2].equals("deluxe"))
                hr = hotel.getDexRoom();
            else
                hr = hotel.getStdRoom();
            Booking booking = new Booking(hotel, UserDAO.getUser((String) data[1]), hr, (Integer) data[3], DateUtil.readFromDB((String) data[4]), DateUtil.readFromDB((String) data[5]), (String) data[6]);
            BookingDAO.addBooking(booking);
        }
    }
    static void closeConn() {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            System.err.println("Error while trying to connect to trivogo.db");
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        populateDB();
    }
}
