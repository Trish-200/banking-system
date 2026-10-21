package bank;

import bank.controller.AccountController;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.view.AccountView;
import bank.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankApp extends Application {

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        loginView.setOnLogin(() -> {
            // Temporary check until the real login is connected to the database
            if (loginView.getUsername().isBlank() || loginView.getPassword().isBlank()) {
                loginView.showError("Enter your username and password.");
                return;
            }
            showAccountScreen(stage);
        });

        stage.setTitle("Banking System");
        stage.setScene(new Scene(loginView.getRoot(), 380, 360));
        stage.show();
    }

    private void showAccountScreen(Stage stage) {
        AccountView accountView = new AccountView();
        new AccountController(createDemoCustomer(), accountView);
        stage.setScene(new Scene(accountView.getRoot(), 460, 560));
    }

    // Temporary demo data until the database is added
    private Customer createDemoCustomer() {
        Customer tee = new Customer("Tee", "Teseletso", "Plot 123, Gaborone");
        SavingsAccount savings = new SavingsAccount(tee, "Main Mall");
        InvestmentAccount investment = new InvestmentAccount(tee, "Main Mall", 1000.00);
        ChequeAccount cheque = new ChequeAccount(tee, "Main Mall", "Acme Ltd", "Plot 7, Gaborone");
        tee.addAccount(savings);
        tee.addAccount(investment);
        tee.addAccount(cheque);
        savings.deposit(2000.00);
        cheque.deposit(8000.00, "Salary");
        return tee;
    }
}
