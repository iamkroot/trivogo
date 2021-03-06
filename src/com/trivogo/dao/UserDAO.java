package com.trivogo.dao;

import java.sql.*;
import java.util.Date;

import com.trivogo.models.User;
import com.trivogo.utils.DateUtil;

public class UserDAO {
    private static Connection conn = DBConn.getConn();
    public static int addUser(User user) {
        try {
            String stmt = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAddress());
            ps.setString(5, DateUtil.convertToDBFormat(user.getDob()));
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
        User user = null;
        try {
            String stmt = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date dob = DateUtil.readFromDB(rs.getString("dob"));
                user = new User(rs.getString("username"), rs.getString("fullName"), rs.getString("email"), rs.getString("address"), dob, rs.getString("passwordHash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
