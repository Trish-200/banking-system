package bank.model;

/**
 * Investment account: pays 5% monthly interest. Allows withdrawals.
 * Can only be opened with an initial deposit of BWP 500.00 or more.
 */
public class InvestmentAccount extends Account implements Withdrawable {
    private static final double RATE = 0.05;
    public static final double MIN_DEPOSIT = 500.00;

    public InvestmentAccount(Customer customer, String branch, double initialDeposit) {
        super(customer, branch);
        if (initialDeposit < MIN_DEPOSIT) {
            throw new IllegalArgumentException(
                    "An Investment Account needs an initial deposit of at least BWP 500.00.");
        }
        deposit(initialDeposit, "Initial deposit");
    }

    @Override
    public void withdraw(double amount) {
        debit(amount);
    }

    @Override
    public void payInterest() {
        addInterest(RATE);
    }
}