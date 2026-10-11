package bank.model;

/**
 * Cheque account: for working people. Deposits and withdrawals of any amount.
 * The customer must give the name and address of their employer. No interest.
 */
public class ChequeAccount extends Account implements Withdrawable {
    private final String employerName;
    private final String employerAddress;

    public ChequeAccount(Customer customer, String branch,
                         String employerName, String employerAddress) {
        super(customer, branch);
        if (isBlank(employerName) || isBlank(employerAddress)) {
            throw new IllegalArgumentException(
                    "A Cheque Account can only be opened for a working person. "
                    + "Employer name and address are required.");
        }
        this.employerName = employerName.trim();
        this.employerAddress = employerAddress.trim();
    }

    @Override
    public void withdraw(double amount) {
        debit(amount);
    }

    /** Cheque accounts do not earn interest, so nothing happens here. */
    @Override
    public void payInterest() {
        // no interest on a Cheque Account
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }

    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}