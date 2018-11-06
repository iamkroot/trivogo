package com.trivogo.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.trivogo.models.User;

public class UserDAO {
    public static void addUser (User user) {
        Connection conn = DBConn.getConn();
        try {
            String stmt = "INSERT INTO users (username, FullName, Email, Address, DOB, Password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAddress());
            ps.setString(5, (new SimpleDateFormat("yyyy-mm-dd")).format(user.getDob()));
            ps.setString(6, user.getPassword());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
    public static User getUser (String username) {
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
                    String strDate = rs.getString("DOB");
                    dob = (new SimpleDateFormat("yyyy-mm-dd")).parse(strDate);
                } catch (ParseException e) {
                    System.err.println("Error while reading dob from database.");
                    System.exit(2);
                }
                user = new User(rs.getString("FullName"), rs.getString("Address"), rs.getString("username"), rs.getString("Password"), rs.getString("Email"), dob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return user;
    }
}
