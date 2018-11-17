package com.trivogo.dao;

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
    }

    private static void createTables() {
        String createUserTable = "DROP TABLE IF EXISTS users;" +
                "CREATE TABLE users (" +
                "username TEXT PRIMARY KEY," +
                "fullName TEXT," +
                "email TEXT," +
                "address TEXT," +
                "dob TEXT," +
                "passwordHash TEXT" +
                ");";
        String createHotelTable = "DROP TABLE IF EXISTS hotels;" +
                "CREATE TABLE hotels (" +
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

    private static void populateHotels() {
        Object hotelData[][] = {
                {"Paris Hilton", "Hyderabad", 200, 300, 2300f, 1500f, "Wifi, Breakfast, Club House", "Wifi"},
                {"Mercure Grand", "New Delhi", 100, 100, 5000f, 3100f, "Wifi, Breakfast, Gym", "Breakfast"},
                {"Taj", "Mumbai", 630, 190, 10000f, 2200f, "Wifi, Breakfast, Club, Gym, Taxi Service", "Wifi, Breakfast"},
                {"Lotus Grand", "Hyderabad", 50, 210, 750f, 400f, "Breakfast", ""}
        };
        try {
            PreparedStatement clearTable = conn.prepareStatement("DELETE FROM hotels");
            clearTable.executeUpdate();
            clearTable.close();
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
