package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A bank customer. A customer can hold many accounts,
 * but only one account of each type.
 */
public class Customer {
    private final String firstName;
    private final String surname;
    private String address;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String surname, String address) {
        if (isBlank(firstName) || isBlank(surname) || isBlank(address)) {
            throw new IllegalArgumentException("First name, surname and address are required.");
        }
        this.firstName = firstName.trim();
        this.surname = surname.trim();
        this.address = address.trim();
    }

    /** Adds an account to this customer. The account must belong to this customer. */
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (account.getCustomer() != this) {
            throw new IllegalArgumentException("This account belongs to a different customer.");
        }
        for (Account existing : accounts) {
            if (existing.getClass() == account.getClass()) {
                throw new IllegalStateException(
                        "Customer already has a " + existing.getClass().getSimpleName() + ".");
            }
        }
        accounts.add(account);
    }

    /** Returns a read-only list so outside code cannot change the accounts directly. */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /** Finds one of this customer's accounts by number, or returns null if not found. */
    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getFullName() { return firstName + " " + surname; }
    public String getAddress() { return address; }

    public void setAddress(String address) {
        if (isBlank(address)) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address.trim();
    }

    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    @Override
    public String toString() {
        return getFullName() + ", " + address;
    }
}