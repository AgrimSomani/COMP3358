package dao;
import java.sql.*;

public class OnlineUserDAO {
    // Insert session
    public static void addSession(String username) throws SQLException {
        String sql = "INSERT INTO OnlineUser (username) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    // Delete session
    public static void removeSession(String username) throws SQLException {
        String sql = "DELETE FROM OnlineUser WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    // Check if user is online
    public static boolean isUserOnline(String username) throws SQLException {
        String sql = "SELECT username FROM OnlineUser WHERE username= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if user has an active session
            }
        }
    }
}
