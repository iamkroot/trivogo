package com.trivogo.dao;

import com.trivogo.models.Hotel;
import com.trivogo.models.Review;
import com.trivogo.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private static Connection conn = DBConn.getConn();

    public static int addReview(int bookingID, Review review) {
        int reviewID = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO reviews (hotelID, user, description, locationParam, roomParam, serviceParam, cleanlinessParam, valueForMoneyParam) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, review.getHotel().getId());
            ps.setString(2, review.getUser().getUsername());
            ps.setString(3, review.getReviewDescription());
            ps.setInt(4, review.getParamLocation());
            ps.setInt(5, review.getParamRoom());
            ps.setInt(6, review.getParamService());
            ps.setInt(7, review.getParamClean());
            ps.setInt(8, review.getParamValueForMoney());
            ps.executeUpdate();
            ps = conn.prepareStatement("SELECT id FROM reviews ORDER BY id DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                reviewID = rs.getInt(1);
                review.setId(reviewID);
                BookingDAO.addReview(bookingID, review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewID;
    }

    private static Review fromResultSet(ResultSet rs) {
        Review review = null;
        try {
            Hotel hotel = HotelDAO.getHotelByID(rs.getInt("hotelID"));
            User user = UserDAO.getUser(rs.getString("user"));
            review = new Review(hotel, user, rs.getString("description"), rs.getInt("locationParam"),
                    rs.getInt("roomParam"), rs.getInt("serviceParam"), rs.getInt("cleanlinessParam"), rs.getInt("valueForMoneyParam"));
            review.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    public static Review getReviewByID(int id) {
        Review review = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM reviews WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                review = fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    public static List<Review> getHotelReviews(Hotel hotel) {
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM reviews where hotelID = ?");
            ps.setInt(1, hotel.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                reviews.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
