package bank;

import bank.dao.DatabaseConnection;

import java.sql.Connection;

public class DbTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to: " + connection.getCatalog());
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
