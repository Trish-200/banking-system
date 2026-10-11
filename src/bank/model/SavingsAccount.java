package bank.model;

/**
 * Savings account: pays 0.05% monthly interest. No withdrawals allowed,
 * so it does not implement Withdrawable.
 */
public class SavingsAccount extends Account {
    private static final double RATE = 0.0005;

    public SavingsAccount(Customer customer, String branch) {
        super(customer, branch);
    }

    @Override
    public void payInterest() {
        addInterest(RATE);
    }
}