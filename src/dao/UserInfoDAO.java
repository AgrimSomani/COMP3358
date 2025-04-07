package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDAO {
    // Insert
    public static void insertUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO UserInfo (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }

    // Authorize 
    public static boolean authorizeUser(String username, String password) throws SQLException {
        String sql = "SELECT username FROM UserInfo WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if user has an active session
            }
        }
    }

    // Check if user registered
    public static boolean isUserRegistered(String username) throws SQLException {
        String sql = "SELECT username FROM UserInfo WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if user is registered with the provided username
            }
        }
    }


    // Read all
    public static List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM UserInfo";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        }
        return users;
    }

    // Update
    public static void updateUserPassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE UserInfo SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    // Delete
    public static void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM UserInfo WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}