package bank.dao;

import bank.model.Account;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for accounts: Create, Read, Update, Delete (CRUD).
 * All SQL for the accounts table lives here.
 */
public class AccountDAO {

    /** CREATE: saves an account that belongs to the given customer_id. */
    public void insert(Account account, int customerId) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, "
                + "branch, date_opened, status, initial_deposit, employer_name, employer_address) "
                + "VALUES (?, ?, ?, ?, ?, CURDATE(), 'Active', ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountNumber());
            statement.setInt(2, customerId);
            statement.setString(3, account.getClass().getSimpleName());
            statement.setDouble(4, account.getBalance());
            statement.setString(5, account.getBranch());
            statement.setDouble(6, account.getBalance());
            if (account instanceof ChequeAccount cheque) {
                statement.setString(7, cheque.getEmployerName());
                statement.setString(8, cheque.getEmployerAddress());
            } else {
                statement.setNull(7, java.sql.Types.VARCHAR);
                statement.setNull(8, java.sql.Types.VARCHAR);
            }
            statement.executeUpdate();
        }
    }

    /**
     * READ: loads all accounts of a customer from the database, with their
     * balances and transaction history, and adds them to the Customer object.
     */
    public List<Account> findByCustomer(int customerId, Customer customer) throws SQLException {
        List<Account> result = new ArrayList<>();
        String sql = "SELECT account_number, account_type, balance, branch, "
                + "employer_name, employer_address FROM accounts WHERE customer_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet row = statement.executeQuery()) {
                while (row.next()) {
                    Account account = buildAccount(customer, row.getString("account_type"),
                            row.getString("branch"), row.getString("employer_name"),
                            row.getString("employer_address"));
                    account.restore(row.getString("account_number"), row.getDouble("balance"));
                    loadTransactions(connection, account);
                    customer.addAccount(account);
                    result.add(account);
                }
            }
        }
        return result;
    }

    /** UPDATE: saves a new balance for an account. */
    public boolean updateBalance(String accountNumber, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);
            return statement.executeUpdate() > 0;
        }
    }

    /** DELETE: removes an account (its transactions are removed first). */
    public boolean delete(String accountNumber) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM transactions WHERE account_number = ?")) {
                statement.setString(1, accountNumber);
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM accounts WHERE account_number = ?")) {
                statement.setString(1, accountNumber);
                return statement.executeUpdate() > 0;
            }
        }
    }

    // ---- helpers ----

    /** Creates the right kind of Account object from the saved account_type. */
    private Account buildAccount(Customer customer, String type, String branch,
                                 String employerName, String employerAddress) {
        switch (type) {
            case "SavingsAccount":
                return new SavingsAccount(customer, branch);
            case "InvestmentAccount":
                // the real balance is put back by restore(), so the minimum is just a placeholder
                return new InvestmentAccount(customer, branch, InvestmentAccount.MIN_DEPOSIT);
            case "ChequeAccount":
                return new ChequeAccount(customer, branch, employerName, employerAddress);
            default:
                throw new IllegalStateException("Unknown account type: " + type);
        }
    }

    /** Loads the saved transaction history of one account, oldest first. */
    private void loadTransactions(Connection connection, Account account) throws SQLException {
        String sql = "SELECT date_time, type, amount, balance_after FROM transactions "
                + "WHERE account_number = ? ORDER BY date_time, transaction_id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountNumber());
            try (ResultSet row = statement.executeQuery()) {
                while (row.next()) {
                    Timestamp time = row.getTimestamp("date_time");
                    account.addRestoredTransaction(new Transaction(time.toLocalDateTime(),
                            row.getString("type"), row.getDouble("amount"),
                            row.getDouble("balance_after")));
                }
            }
        }
    }
}