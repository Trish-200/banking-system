package bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * One line in an account's transaction history.
 * Immutable: once created it cannot be changed.
 */
public class Transaction {
    private final LocalDateTime dateTime;
    private final String type;
    private final double amount;
    private final double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    /** Used by the DAO only: rebuilds a saved transaction with its original date and time. */
    public Transaction(LocalDateTime dateTime, String type, double amount, double balanceAfter) {
        this.dateTime = dateTime;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getDateTime() { return dateTime; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s | %-16s | BWP %10.2f | Balance BWP %10.2f",
                dateTime.format(fmt), type, amount, balanceAfter);
    }
}