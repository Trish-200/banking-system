package bank;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.dao.UserDAO;
import bank.model.Account;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.model.Transaction;

/** Puts 10 sample customers (with accounts, history and logins) into the database. */
public class SeedData {

    private static final CustomerDAO customerDao = new CustomerDAO();
    private static final AccountDAO accountDao = new AccountDAO();
    private static final TransactionDAO transactionDao = new TransactionDAO();
    private static final UserDAO userDao = new UserDAO();

    public static void main(String[] args) {
        try {
            if (!customerDao.findAll().isEmpty()) {
                System.out.println("Sample data is already in the database. Nothing was added.");
                return;
            }

            String[][] people = {
                    {"Neo", "Kgosi", "Plot 12, Gaborone"},
                    {"Lesedi", "Moeng", "Block 8, Francistown"},
                    {"Tumelo", "Sebego", "Plot 301, Maun"},
                    {"Kefilwe", "Ndlovu", "Plot 45, Palapye"},
                    {"Thabo", "Molefe", "Block 3, Lobatse"},
                    {"Boitumelo", "Dikgang", "Plot 77, Serowe"},
                    {"Onkarabile", "Mosweu", "Plot 9, Kasane"},
                    {"Masego", "Phiri", "Block 14, Selebi-Phikwe"},
                    {"Gofaone", "Tau", "Plot 220, Mahalapye"},
                    {"Naledi", "Segwabe", "Plot 56, Molepolole"}
            };

            for (int i = 0; i < people.length; i++) {
                Customer customer = new Customer(people[i][0], people[i][1], people[i][2]);
                int id = customerDao.insert(customer);

                // everyone gets a Savings Account
                SavingsAccount savings = new SavingsAccount(customer, "Gaborone Main");
                savings.deposit(1000 + i * 250);
                customer.addAccount(savings);
                save(savings, id);

                // some also get an Investment Account
                if (i % 3 == 0 || i % 3 == 2) {
                    InvestmentAccount investment =
                            new InvestmentAccount(customer, "Gaborone Main", 500 + i * 100);
                    customer.addAccount(investment);
                    save(investment, id);
                }

                // some also get a Cheque Account
                if (i % 3 == 1 || i % 3 == 2) {
                    ChequeAccount cheque = new ChequeAccount(customer, "Gaborone Main",
                            "Botswana Employer " + (i + 1) + " Ltd", "Plot " + (i + 1) + ", Gaborone");
                    cheque.deposit(3000 + i * 500, "Salary");
                    customer.addAccount(cheque);
                    save(cheque, id);
                }

                // a login for the customer: username is the first name in lowercase
                userDao.insert(customer.getFullName(), people[i][0].toLowerCase(),
                        "pass123", "customer", id);
            }

            // one staff login that is not linked to a customer
            userDao.insert("Bank Teller", "teller", "teller123", "teller", null);

            System.out.println("Done. Customers in database: " + customerDao.findAll().size());
            System.out.println("Customer logins: username = first name in lowercase "
                    + "(for example neo), password = pass123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Saves an account and its history to the database. */
    private static void save(Account account, int customerId) throws Exception {
        accountDao.insert(account, customerId);
        for (Transaction transaction : account.getTransactions()) {
            transactionDao.insert(account.getAccountNumber(), transaction);
        }
    }
}
