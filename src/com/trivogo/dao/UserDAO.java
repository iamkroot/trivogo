package com.trivogo.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.trivogo.models.User;

public class UserDAO {
    public static int addUser(User user) {
        Connection conn = DBConn.getConn();
        try {
            String stmt = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAddress());
            ps.setString(5, (new SimpleDateFormat("yyyy-mm-dd")).format(user.getDob()));
            ps.setString(6, user.getPassword());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;  // random SQL Error
        }
        return 1;
    }

    public static boolean checkUserExists(String username) {
        Connection conn = DBConn.getConn();
        String queryCheck = "SELECT count(*) from users WHERE username = ?";
        try {
            PreparedStatement ps1 = conn.prepareStatement(queryCheck);
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();
            ps1.close();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // user already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUser(String username) {
        Connection conn = DBConn.getConn();
        User user = null;
        try {
            String stmt = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.util.Date dob = null;
                try {
                    String strDate = rs.getString("dob");
                    dob = (new SimpleDateFormat("yyyy-mm-dd")).parse(strDate);
                } catch (ParseException e) {
                    System.err.println("Error while reading dob from database.");
                    return null;
                }
                user = new User(rs.getString("fullName"), rs.getString("address"), rs.getString("username"), rs.getString("passwordHash"), rs.getString("email"), dob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
