package bank.controller;

import bank.model.Account;
import bank.model.Customer;
import bank.model.Transaction;
import bank.model.Withdrawable;
import bank.view.AccountView;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** Controller: connects AccountView (boundary) to the model classes. */
public class AccountController {

    private final Customer customer;
    private final AccountView view;

    public AccountController(Customer customer, AccountView view) {
        this.customer = customer;
        this.view = view;

        List<String> labels = customer.getAccounts().stream()
                .map(a -> a.getAccountNumber() + " - " + a.getClass().getSimpleName())
                .collect(Collectors.toList());
        view.setAccounts(labels);

        view.setOnAccountChanged(this::refresh);
        view.setOnDeposit(this::deposit);
        view.setOnWithdraw(this::withdraw);
    }

    private Account selectedAccount() {
        String label = view.getSelectedAccount();
        if (label == null) {
            return null;
        }
        return customer.findAccount(label.split(" ")[0]);
    }

    private void refresh() {
        Account account = selectedAccount();
        if (account == null) {
            return;
        }
        view.setBalance(String.format("BWP %.2f", account.getBalance()));
        view.setHistory(account.getTransactions().stream()
                .map(Transaction::toString)
                .collect(Collectors.toList()));
    }

    private double readAmount() {
        try {
            return Double.parseDouble(view.getAmountText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a valid amount.");
        }
    }

    private void deposit() {
        perform(account -> account.deposit(readAmount()), "Deposit successful.");
    }

    private void withdraw() {
        perform(account -> {
            if (account instanceof Withdrawable withdrawable) {
                withdrawable.withdraw(readAmount());
            } else {
                throw new IllegalArgumentException("This account type does not allow withdrawals.");
            }
        }, "Withdrawal successful.");
    }

    private void perform(Consumer<Account> action, String successMessage) {
        Account account = selectedAccount();
        if (account == null) {
            view.showMessage("Select an account first.", true);
            return;
        }
        try {
            action.accept(account);
            view.clearAmount();
            refresh();
            view.showMessage(successMessage, false);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage(), true);
        }
    }
}
