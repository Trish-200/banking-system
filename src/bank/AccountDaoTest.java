package bank;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.model.Account;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.model.Transaction;

public class AccountDaoTest {
    public static void main(String[] args) {
        CustomerDAO customerDao = new CustomerDAO();
        AccountDAO accountDao = new AccountDAO();
        TransactionDAO transactionDao = new TransactionDAO();
        try {
            // 1. Save a customer with all three account types
            Customer customer = new Customer("Neo", "Kgosi", "Plot 12, Gaborone");
            int id = customerDao.insert(customer);

            SavingsAccount savings = new SavingsAccount(customer, "Gaborone Main");
            savings.deposit(2000);
            InvestmentAccount investment = new InvestmentAccount(customer, "Gaborone Main", 1000);
            investment.withdraw(200);
            ChequeAccount cheque = new ChequeAccount(customer, "Gaborone Main",
                    "Acme Ltd", "Plot 5, Gaborone");
            cheque.deposit(8000, "Salary");

            customer.addAccount(savings);
            customer.addAccount(investment);
            customer.addAccount(cheque);

            for (Account account : customer.getAccounts()) {
                accountDao.insert(account, id);
                for (Transaction transaction : account.getTransactions()) {
                    transactionDao.insert(account.getAccountNumber(), transaction);
                }
            }
            System.out.println("1. Saved customer " + id + " with 3 accounts");

            // 2. Load everything back into a brand new Customer object
            Customer loaded = customerDao.findById(id);
            accountDao.findByCustomer(id, loaded);
            System.out.println("2. Loaded from database:");
            for (Account account : loaded.getAccounts()) {
                System.out.println("   " + account);
            }

            // 3. Transaction history
            System.out.println("3. Investment history from database:");
            for (Transaction transaction : transactionDao.findByAccount(investment.getAccountNumber())) {
                System.out.println("   " + transaction);
            }

            // 4. Update a balance and read it again
            accountDao.updateBalance(savings.getAccountNumber(), 2500.00);
            Customer reloaded = customerDao.findById(id);
            accountDao.findByCustomer(id, reloaded);
            System.out.println("4. Savings balance after update: "
                    + reloaded.findAccount(savings.getAccountNumber()).getBalance());

            // 5. Delete everything this test created
            for (Account account : customer.getAccounts()) {
                accountDao.delete(account.getAccountNumber());
            }
            customerDao.delete(id);
            System.out.println("5. Cleaned up. Customer now: " + customerDao.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}