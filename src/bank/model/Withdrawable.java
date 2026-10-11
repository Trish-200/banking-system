package bank.model;

/**
 * Interface for accounts that allow withdrawals.
 * SavingsAccount does NOT implement this, so it can never be withdrawn from.
 */
public interface Withdrawable {
    void withdraw(double amount);
}