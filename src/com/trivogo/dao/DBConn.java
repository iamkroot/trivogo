package com.trivogo.dao;

import com.trivogo.models.User;
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
        populateHotels();
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
                {"Hilton", "Tom Cruise", "Deluxe", 5, "2018/11/19", "2018/11/23", "CONFIRMED"},
                {"Leela Palace", "Kruut Patel", "Standard", 1,  "2018/12/1", "2018/12/2", "CONFIRMED"},
                {"Grand Hilton", "Guru Ram", "Standard", 13, "2018/11/23", "2018/11/27", "CONFIRMED"},
                {"Fort House Inn", "Gurpreet Singh", "Deluxe", 1, "2018/12/23", "2019/01/02", "CONFIRMED"},
                {"Novotel", "Arjun Reddy", "Deluxe", 10, "2018/11/28", "2018/12/03", "CONFIRMED"},
                {"Leonia", "Shah Rukh Khan", "Deluxe", 3, "2018/12/12", "2018/12/15", "CONFIRMED"},
                {"Taj", "Amitabh Bachchan", "Deluxe", 2, "2018/12/20", "2019/01/03", "CONFIRMED"},
                {"Ramada", "Alphabet Inc", "Deluxe", 12, "2018/12/15", "2019/01/05", "CONFIRMED"},
                {"Mercure Grand", "Liberty Media", "Standard", 56, "2018/12/13", "2018/12/19", "CONFIRMED"},
                {"Ramada", "Rahul Nair", "Deluxe", 1, "2018/12/23", "2018/12/28", "WAITLIST"},
                {"Mercure Grand", "Rohan Bhatt", "Standard", 1, "2018/12/01", "2018/12/02", "CONFIRMED"},
                /*{"HOTELNAME", "USERNAME", "Standard", NUMROOM, "INDATE", "OUTDATE", "STATUS"}*/

        };
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
