package bank;

import bank.model.*;

/**
 * Console test of the core model (no GUI or database yet).
 * Run it to check that every rule from the assignment works.
 */
public class Main {
    public static void main(String[] args) {
        Customer tee = new Customer("Tee", "Teseletso", "Plot 123, Gaborone");

        // Open one of each account type
        SavingsAccount savings = new SavingsAccount(tee, "Main Mall");
        InvestmentAccount investment = new InvestmentAccount(tee, "Main Mall", 1000.00);
        ChequeAccount cheque = new ChequeAccount(tee, "Main Mall", "Acme Ltd", "Plot 7, Gaborone");
        tee.addAccount(savings);
        tee.addAccount(investment);
        tee.addAccount(cheque);

        // Deposits (overloaded deposit methods)
        savings.deposit(2000.00);
        cheque.deposit(8000.00, "Salary");

        // Withdrawals (only Investment and Cheque can)
        investment.withdraw(200.00);
        cheque.withdraw(1500.00);

        // Polymorphism: same call, different behaviour for each account type
        for (Account account : tee.getAccounts()) {
            account.payInterest();
        }

        System.out.println("=== ACCOUNTS ===");
        for (Account account : tee.getAccounts()) {
            System.out.println(account);
        }

        System.out.println();
        System.out.println("=== INVESTMENT TRANSACTION HISTORY ===");
        for (Transaction t : investment.getTransactions()) {
            System.out.println(t);
        }

        System.out.println();
        System.out.println("=== RULE CHECKS (each should print an error message) ===");
        check("Investment below BWP500", () -> new InvestmentAccount(tee, "Main Mall", 100.00));
        check("Cheque without employer", () -> new ChequeAccount(tee, "Main Mall", "", ""));
        check("Negative deposit", () -> savings.deposit(-50.00));
        check("Withdraw more than balance", () -> cheque.withdraw(999999.00));
        check("Second Savings account", () -> tee.addAccount(new SavingsAccount(tee, "Main Mall")));
        check("Account with no customer", () -> new SavingsAccount(null, "Main Mall"));
        // savings.withdraw(100);  <-- will not even compile: SavingsAccount has no withdraw()
    }

    private static void check(String name, Runnable action) {
        try {
            action.run();
            System.out.println("FAIL - " + name + ": no error was thrown");
        } catch (RuntimeException e) {
            System.out.println("OK   - " + name + ": " + e.getMessage());
        }
    }
}