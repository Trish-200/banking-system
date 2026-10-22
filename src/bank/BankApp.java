package bank;

import bank.controller.AccountController;
import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.UserDAO;
import bank.model.Customer;
import bank.view.AccountView;
import bank.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BankApp extends Application {

    private final UserDAO userDao = new UserDAO();
    private final CustomerDAO customerDao = new CustomerDAO();
    private final AccountDAO accountDao = new AccountDAO();

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        loginView.setOnLogin(() -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            if (username.isBlank() || password.isBlank()) {
                loginView.showError("Enter your username and password.");
                return;
            }
            try {
                UserDAO.LoginResult result = userDao.authenticate(username.trim(), password);
                if (result == null) {
                    loginView.showError("Wrong username or password.");
                } else if (result.customerId() == null) {
                    loginView.showError("This is a staff login. Please use a customer login.");
                } else {
                    showAccountScreen(stage, result.customerId());
                }
            } catch (SQLException e) {
                loginView.showError("Database problem: " + e.getMessage());
            }
        });

        stage.setTitle("Banking System");
        stage.setScene(new Scene(loginView.getRoot(), 380, 360));
        stage.show();
        loginView.getRoot().requestFocus();
    }

    /** Loads the customer and their accounts from the database and shows the account screen. */
    private void showAccountScreen(Stage stage, int customerId) throws SQLException {
        Customer customer = customerDao.findById(customerId);
        accountDao.findByCustomer(customerId, customer);

        AccountView accountView = new AccountView();
        new AccountController(customer, accountView);
        stage.setTitle("Banking System - " + customer.getFullName());
        stage.setScene(new Scene(accountView.getRoot(), 460, 560));
    }
}