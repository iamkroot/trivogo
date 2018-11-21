package com.trivogo.dao;

import com.trivogo.models.*;
import com.trivogo.utils.DateUtil;
import com.trivogo.utils.Hasher;
import java.math.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

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
//        populateReviews();
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

                {2, "vg", "Standard", 5, "2018-11-01", "2018-11-10", "CONFIRMED"},
                {3, "1", "Deluxe", 2, "2018-11-03", "2018-11-12", "CONFIRMED"},
                {1, "vg", "Deluxe", 3, "2018-11-02", "2018-11-12", "CONFIRMED"},
                {4, "dv", "Standard", 6, "2018-11-05", "2018-11-11", "CONFIRMED"},
                {6, "asdf", "Standard", 3, "2018-11-01", "2018-11-10", "CONFIRMED"},
                {5, "1", "Deluxe", 7, "2018-11-02", "2018-11-10", "CONFIRMED"},
                {7, "asdf", "Deluxe", 1, "2018-11-05", "2018-11-12", "CONFIRMED"},
                {9, "vg", "Standard", 1, "2018-11-08", "2018-11-19", "CONFIRMED"},
                {8, "asdf", "Standard", 1, "2018-11-02", "2018-11-15", "CONFIRMED"},
                {11, "dv", "Deluxe", 2, "2018-11-09", "2018-11-12", "CONFIRMED"},
                {10, "vg", "Deluxe", 5, "2018-11-07", "2018-11-12", "CONFIRMED"},
                {12, "asdf", "Standard", 1, "2018-11-08", "2018-11-14", "CONFIRMED"},
                {13, "1", "Standard", 2, "2018-11-05", "2018-11-17", "CONFIRMED"},
                {2, "asdf", "Deluxe", 1, "2018-11-06", "2018-11-15", "CONFIRMED"},
                {3, "vg", "Standard", 4, "2018-11-03", "2018-11-21", "CONFIRMED"},
                {1, "asdf", "Standard", 1, "2018-11-05", "2018-11-12", "CONFIRMED"},
                {4, "dv", "Deluxe", 3, "2018-11-03", "2018-11-17", "CONFIRMED"},
                {6, "vg", "Standard", 4, "2018-11-01", "2018-11-17", "CONFIRMED"},
                {5, "asdf", "Deluxe", 1, "2018-11-03", "2018-11-17", "CONFIRMED"},
                {7, "asdf", "Standard", 3, "2018-11-01", "2018-11-15", "CONFIRMED"},
                {9, "vg", "Standard", 4, "2018-11-05", "2018-11-15", "CONFIRMED"},
                {8, "asdf", "Deluxe", 1, "2018-11-02", "2018-11-17", "CONFIRMED"},
                {11, "asdf", "Standard", 2, "2018-11-07", "2018-11-13", "CONFIRMED"},
                {10, "vg", "Deluxe", 1, "2018-11-03", "2018-11-17", "CONFIRMED"},
                {12, "asdf", "Deluxe", 1, "2018-11-09", "2018-11-15", "CONFIRMED"},
                {13, "asdf", "Standard", 5, "2018-11-01", "2018-11-12", "CONFIRMED"},


                {1, "asdf", "Deluxe", 5, "2018-11-19", "2018-11-23", "CONFIRMED"},
                {4, "dv", "Standard", 1,  "2018-12-1", "2018-12-2", "CONFIRMED"},
                {9, "pp", "Standard", 13, "2018-11-23", "2018-11-27", "CONFIRMED"},
                {7, "dv", "Deluxe", 1, "2018-12-23", "2019-01-02", "CONFIRMED"},
                {5, "asdf", "Deluxe", 10, "2018-11-28", "2018-12-03", "CONFIRMED"},
                {13, "pp", "Deluxe", 3, "2018-12-12", "2018-12-15", "CONFIRMED"},
                {3, "1", "Deluxe", 2, "2018-12-20", "2019-01-03", "CONFIRMED"},
                {10, "asdf", "Deluxe", 11, "2018-12-15", "2019-01-05", "CONFIRMED"},
                {2, "dv", "Standard", 56, "2018-12-13", "2018-12-19", "CONFIRMED"},
                {10, "pp", "Standard", 1, "2018-12-23", "2018-12-28", "WAITLIST PENDING"},
                {2, "asdf", "Standard", 1, "2018-12-01", "2018-12-02", "CONFIRMED"},
                {1, "1", "Deluxe", 1, "2018-11-16", "2018-11-19", "CANCELLED"},
                {4, "asdf", "Standard",45, "2018-11-25", "2018-12-15", "CONFIRMED"},
                {7, "1", "Deluxe", 3, "2018-12-22", "2019-01-02", "CANCELLED"},
                {10, "1", "Deluxe", 1, "2018-12-20", "2018-12-22", "CONFIRMED"},
                {6, "1", "Deluxe", 5, "2018-11-25", "2018-11-30", "CONFIRMED"},
                {7, "dv", "Deluxe", 2, "2018-12-23", "2019-01-02", "WAITLIST CONFIRMED"},
                {2, "1", "Standard", 42, "2018-12-10", "2018-12-26", "CONFIRMED"},
                {9, "asdf", "Standard", 14, "2018-12-13", "2018-12-16", "CANCELLED"},
                {8, "1", "Standard", 250, "2018-12-15", "2018-12-27", "CONFIRMED"},
                {7, "1", "Standard", 10, "2018-12-23", "2018-12-27", "CONFIRMED"},
                {6, "1", "Deluxe", 45, "2018-12-11", "2018-12-20", "CONFIRMED"},
                {11, "1", "Standard", 78, "2018-12-02", "2018-12-08", "CONFIRMED"},
                {12, "dv", "Deluxe", 12, "2018-12-06", "2018-12-15", "CONFIRMED"}

                /*These intros should cause waitlist
                {2, "dv", "Standard", 10, "2018-12-15", "2018-12-17", "<waitlist>"},
                {4, "pp", "Deluxe", 7, "2018-12-01", "2018-11-04", "<waitlist>"},
                {7, "asdf", "Deluxe", 3, "2018-12-24", "2018-12-26", "<waitlist>"},
                {7, "asdf", "Standard", 1, "2018-12-24", "2018-12-26", "<waitlist>"},
                {10, "dv", "Deluxe", 2, "2018-12-18", "2018-12-23", "<waitlist>"},
                {6, "1", "Deluxe", 10, "2018-12-15", "2018-12-20", "<waitlist>"},
                {INTHOTELID, "asdf", "Standard", INTNUMROOMS, "INDATE", "OUTDATE", "<waitlit>"}*/

        };
        for (int i = 0; i < bookingData.length; i++) {
            Object[] data = bookingData[i];
            Hotel hotel = HotelDAO.getHotelByID((Integer) data[0]);
            HotelRoom hr;
            if(data[2].equals("Deluxe"))
                hr = hotel.getDexRoom();
            else
                hr = hotel.getStdRoom();
            Booking booking = new Booking(hotel, UserDAO.getUser((String) data[1]), hr, (Integer) data[3], DateUtil.readFromDB((String) data[4]), DateUtil.readFromDB((String) data[5]), (String) data[6]);
            BookingDAO.addBooking(booking);
        }
    }

    private static void populateReviews() {
        Random rand = new Random();
        Object[][] reviewData = {
                // {bookingID, hotelID, username, description, params}
                {1, "Pretty nice", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {2, "Good location", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {3, "Nice service", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {4, "Enjoyed our stay", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {5, "Was a grand success", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {6, "Could be better", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {7, "Sucked", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {8, "Awful", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {9, "Had a great time", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {10, "Service was nice", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {11, "Enjoyed swimming in the pool", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {12, "Saw a celebrity", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {13, "There was rat in the kitchen", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {14, "Bed was stained", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {15, "Bathroom was  dirty when we checked in", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {16, "Tasty food", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {17, "Happy vacation spot", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {18, "Best vacation we've been on", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {19, "Had fun", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {20, "Could be better", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {21, "Wasn't that great", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {22, "Decent", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {23, "Nice", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {24, "Pretty fun", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {25, "Great time", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1},
                {26, "Lovely", rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1}
        };
        for (Object[] data: reviewData) {
            Booking booking = BookingDAO.getBookingByID((Integer) data[0]);
            Review review = new Review(booking.getHotel(), booking.getUser(), (String) data[1], (int) data[2], (int) data[3], (int) data[4], (int) data[5], (int) data[6]);
            ReviewDAO.addReview((Integer) data[0], review);
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
