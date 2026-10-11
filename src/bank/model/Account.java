package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract parent of all account types.
 * An account cannot exist without a customer who holds it.
 */
public abstract class Account {
    private static int counter = 0;

    protected final String accountNumber;
    protected double balance;
    protected final String branch;
    private final Customer customer;
    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(Customer customer, String branch) {
        if (customer == null) {
            throw new IllegalArgumentException("An account must be held by a customer.");
        }
        if (branch == null || branch.trim().isEmpty()) {
            throw new IllegalArgumentException("Branch is required.");
        }
        this.customer = customer;
        this.branch = branch.trim();
        this.accountNumber = String.format("ACC%05d", ++counter);
        this.balance = 0.0;
    }

    // ---- Overloading: two versions of deposit() ----

    /** Deposits an amount using the default description "Deposit". */
    public void deposit(double amount) {
        deposit(amount, "Deposit");
    }

    /** Deposits an amount with a custom description for the transaction history. */
    public void deposit(double amount, String description) {
        requirePositive(amount);
        balance = round(balance + amount);
        transactions.add(new Transaction(description, amount, balance));
    }

    /** Removes money from the balance. Only withdrawable accounts expose this publicly. */
    protected void debit(double amount) {
        requirePositive(amount);
        if (amount > balance) {
            throw new IllegalArgumentException(
                    String.format("Insufficient funds. Balance is BWP %.2f.", balance));
        }
        balance = round(balance - amount);
        transactions.add(new Transaction("Withdrawal", amount, balance));
    }

    /** Adds one month of interest at the given rate (for example 0.05 = 5%). */
    protected void addInterest(double rate) {
        double interest = round(balance * rate);
        if (interest <= 0) {
            return;
        }
        balance = round(balance + interest);
        transactions.add(new Transaction("Interest", interest, balance));
    }

    /** Abstraction: each account type decides how its interest is paid. */
    public abstract void payInterest();

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public Customer getCustomer() { return customer; }

    /** Returns a read-only copy of the transaction history. */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    private static void requirePositive(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] %s - Balance: BWP %.2f",
                getClass().getSimpleName(), accountNumber, customer.getFullName(), balance);
    }
}