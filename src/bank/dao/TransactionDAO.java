
package bank.dao;

import bank.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for transactions: saves and reads the history of an account.
 * All SQL for the transactions table lives here.
 */
public class TransactionDAO {

    /** CREATE: saves one transaction for the given account. */
    public void insert(String accountNumber, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, date_time, type, amount, balance_after) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            statement.setTimestamp(2, Timestamp.valueOf(transaction.getDateTime()));
            statement.setString(3, transaction.getType());
            statement.setDouble(4, transaction.getAmount());
            statement.setDouble(5, transaction.getBalanceAfter());
            statement.executeUpdate();
        }
    }

    /** READ: returns the history of an account, oldest first. */
    public List<Transaction> findByAccount(String accountNumber) throws SQLException {
        List<Transaction> result = new ArrayList<>();
        String sql = "SELECT date_time, type, amount, balance_after FROM transactions "
                + "WHERE account_number = ? ORDER BY date_time, transaction_id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            try (ResultSet row = statement.executeQuery()) {
                while (row.next()) {
                    result.add(new Transaction(row.getTimestamp("date_time").toLocalDateTime(),
                            row.getString("type"), row.getDouble("amount"),
                            row.getDouble("balance_after")));
                }
            }
        }
        return result;
    }

    /** DELETE: removes all transactions of an account. */
    public int deleteByAccount(String accountNumber) throws SQLException {
        String sql = "DELETE FROM transactions WHERE account_number = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            return statement.executeUpdate();
        }
    }
}