package bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Data Access Object for users (login). Passwords are never stored as plain text:
 * the database keeps a SHA-256 hash and compares hashes when someone logs in.
 */
public class UserDAO {

    /** What we learn about a user after a successful login. */
    public record LoginResult(String fullName, String role, Integer customerId) { }

    /** CREATE: saves a new user. customerId can be null for staff. */
    public void insert(String fullName, String username, String password,
                       String role, Integer customerId) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, password_hash, role, customer_id) "
                + "VALUES (?, ?, SHA2(?, 256), ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fullName);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, role);
            if (customerId == null) {
                statement.setNull(5, Types.INTEGER);
            } else {
                statement.setInt(5, customerId);
            }
            statement.executeUpdate();
        }
    }

    /** READ: checks a username and password. Returns null if they are wrong. */
    public LoginResult authenticate(String username, String password) throws SQLException {
        String sql = "SELECT full_name, role, customer_id FROM users "
                + "WHERE username = ? AND password_hash = SHA2(?, 256)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet row = statement.executeQuery()) {
                if (row.next()) {
                    int customerId = row.getInt("customer_id");
                    Integer id = row.wasNull() ? null : customerId;
                    return new LoginResult(row.getString("full_name"), row.getString("role"), id);
                }
                return null;
            }
        }
    }

    /** UPDATE: changes a user's password. */
    public boolean updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password_hash = SHA2(?, 256) WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            return statement.executeUpdate() > 0;
        }
    }

    /** DELETE: removes a user. */
    public boolean delete(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        }
    }
}