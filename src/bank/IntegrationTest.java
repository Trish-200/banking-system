package bank;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.dao.UserDAO;
import bank.model.Account;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.SavingsAccount;
import bank.model.Transaction;
import bank.model.Withdrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Integration test: follows one user flow through the layers
 * login (UserDAO) -> load data (DAOs) -> transaction (model) -> save (DAOs) -> reload.
 * It creates its own test customer and removes it again at the end.
 */
public class IntegrationTest {

    private static final CustomerDAO customerDao = new CustomerDAO();
    private static final AccountDAO accountDao = new AccountDAO();
    private static final TransactionDAO transactionDao = new TransactionDAO();
    private static final UserDAO userDao = new UserDAO();

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
        if (ok) {
            passed++;
        } else {
            failed++;
        }
    }

    public static void main(String[] args) {
        int customerId = -1;
        List<String> accountNumbers = new ArrayList<>();
        try {
            accountDao.syncAccountCounter();
            userDao.delete("itest");

            // ---- SETUP: a test customer with a login and two accounts ----
            Customer setup = new Customer("Test", "Customer", "Plot 1, Gaborone");
            customerId = customerDao.insert(setup);
            SavingsAccount savingsSetup = new SavingsAccount(setup, "Gaborone Main");
            savingsSetup.deposit(2000);
            ChequeAccount chequeSetup = new ChequeAccount(setup, "Gaborone Main",
                    "Test Employer Ltd", "Plot 2, Gaborone");
            chequeSetup.deposit(1000, "Salary");
            setup.addAccount(savingsSetup);
            setup.addAccount(chequeSetup);
            for (Account account : setup.getAccounts()) {
                accountDao.insert(account, customerId);
                for (Transaction transaction : account.getTransactions()) {
                    transactionDao.insert(account.getAccountNumber(), transaction);
                }
                accountNumbers.add(account.getAccountNumber());
            }
            userDao.insert("Test Customer", "itest", "itest123", "customer", customerId);
            String savingsNumber = savingsSetup.getAccountNumber();
            String chequeNumber = chequeSetup.getAccountNumber();
            System.out.println("=== INTEGRATION TEST ===");

            // ---- STEP 1: login (database check of the user) ----
            check("1a. Wrong password is rejected",
                    userDao.authenticate("itest", "wrong") == null);
            UserDAO.LoginResult login = userDao.authenticate("itest", "itest123");
            check("1b. Correct password logs in as the right customer",
                    login != null && login.customerId() != null && login.customerId() == customerId);

            // ---- STEP 2: load the customer and accounts from the database ----
            Customer customer = customerDao.findById(login.customerId());
            accountDao.findByCustomer(login.customerId(), customer);
            check("2a. Two accounts loaded from the database",
                    customer.getAccounts().size() == 2);
            Account cheque = customer.findAccount(chequeNumber);
            Account savings = customer.findAccount(savingsNumber);
            check("2b. Cheque balance loaded correctly (1000.00)",
                    cheque != null && Math.abs(cheque.getBalance() - 1000.00) < 0.001);

            // ---- STEP 3: transactions in the core model ----
            int before = cheque.getTransactions().size();
            cheque.deposit(500);
            ((Withdrawable) cheque).withdraw(200);
            check("3a. Deposit 500 and withdraw 200 gives 1300.00",
                    Math.abs(cheque.getBalance() - 1300.00) < 0.001);
            check("3b. Savings Account does not allow withdrawals",
                    !(savings instanceof Withdrawable));
            try {
                ((Withdrawable) cheque).withdraw(999999);
                check("3c. Withdrawing more than the balance is blocked", false);
            } catch (IllegalArgumentException e) {
                check("3c. Withdrawing more than the balance is blocked", true);
            }

            // ---- STEP 4: save through the JDBC classes ----
            List<Transaction> all = cheque.getTransactions();
            for (int i = before; i < all.size(); i++) {
                transactionDao.insert(chequeNumber, all.get(i));
            }
            accountDao.updateBalance(chequeNumber, cheque.getBalance());

            // ---- STEP 5: reload from the database (as if the app was reopened) ----
            Customer reopened = customerDao.findById(customerId);
            accountDao.findByCustomer(customerId, reopened);
            Account chequeAgain = reopened.findAccount(chequeNumber);
            Account savingsAgain = reopened.findAccount(savingsNumber);
            check("5a. New cheque balance was saved (1300.00)",
                    Math.abs(chequeAgain.getBalance() - 1300.00) < 0.001);
            check("5b. Cheque history has 3 entries (Salary, Deposit, Withdrawal)",
                    chequeAgain.getTransactions().size() == 3);
            check("5c. Savings balance is unchanged (2000.00)",
                    Math.abs(savingsAgain.getBalance() - 2000.00) < 0.001);

        } catch (Exception e) {
            failed++;
            System.out.println("ERROR: the test stopped because of an exception:");
            e.printStackTrace();
        } finally {
            // ---- CLEAN UP: remove everything the test created ----
            try {
                userDao.delete("itest");
                for (String number : accountNumbers) {
                    accountDao.delete(number);
                }
                if (customerId > 0) {
                    customerDao.delete(customerId);
                }
            } catch (Exception e) {
                System.out.println("Clean-up problem: " + e.getMessage());
            }
            System.out.println("=== RESULT: " + passed + " passed, " + failed + " failed ===");
        }
    }
}



