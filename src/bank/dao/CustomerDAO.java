package bank.dao;

import bank.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for customers: Create, Read, Update, Delete (CRUD).
 * All SQL for the customers table lives here.
 */
public class CustomerDAO {

    /** CREATE: saves a customer and returns the new customer_id. */
    public int insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, surname, address, date_registered) "
                + "VALUES (?, ?, ?, CURDATE())";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getSurname());
            statement.setString(3, customer.getAddress());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getInt(1);
            }
        }
    }

    /** READ: finds one customer by id, or returns null if not found. */
    public Customer findById(int customerId) throws SQLException {
        String sql = "SELECT first_name, surname, address FROM customers WHERE customer_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet row = statement.executeQuery()) {
                if (row.next()) {
                    return new Customer(row.getString("first_name"),
                            row.getString("surname"), row.getString("address"));
                }
                return null;
            }
        }
    }

    /** READ: returns every customer as a readable line. */
    public List<String> findAll() throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, surname, address FROM customers";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet row = statement.executeQuery()) {
            while (row.next()) {
                result.add(row.getInt("customer_id") + " - " + row.getString("first_name")
                        + " " + row.getString("surname") + ", " + row.getString("address"));
            }
        }
        return result;
    }

    /** UPDATE: changes a customer's address. */
    public boolean updateAddress(int customerId, String newAddress) throws SQLException {
        String sql = "UPDATE customers SET address = ? WHERE customer_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newAddress);
            statement.setInt(2, customerId);
            return statement.executeUpdate() > 0;
        }
    }

    /** DELETE: removes a customer (only works if they have no accounts). */
    public boolean delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            return statement.executeUpdate() > 0;
        }
    }
}
