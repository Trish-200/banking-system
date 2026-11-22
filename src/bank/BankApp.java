package bank;

import bank.controller.AccountController;
import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.UserDAO;
import bank.model.Customer;
import bank.view.AccountView;
import bank.view.LoginView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Main JavaFX application. Shows the login screen, checks the username and password,
 * then opens the staff screen (teller, manager, auditor, administrator) or the
 * customer's account screen.
 */

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
                    showStaffScreen(stage, result);
                } else {
                    showAccountScreen(stage, result.customerId());
                }
            } catch (SQLException e) {
                loginView.showError("Database problem: " + e.getMessage());
            }
        });

        stage.setTitle("Banking System");
        stage.setScene(new Scene(loginView.getRoot(), 380, 360));
        stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
        loginView.getRoot().requestFocus();
    }

    /** Staff (teller or administrator) choose which customer to serve. */
    private void showStaffScreen(Stage stage, UserDAO.LoginResult staff) throws SQLException {
        Label title = new Label("Welcome, " + staff.fullName() + " (" + staff.role() + ")");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label prompt = new Label("Choose a customer to serve:");

        ComboBox<String> customers = new ComboBox<>();
        customers.getItems().addAll(customerDao.findAll());
        customers.setMaxWidth(Double.MAX_VALUE);

        Button open = new Button("Open customer accounts");
        Label message = new Label();
        message.setStyle("-fx-text-fill: #c0392b;");

        open.setOnAction(e -> {
            String chosen = customers.getValue();
            if (chosen == null) {
                message.setText("Select a customer first.");
                return;
            }
            try {
                showAccountScreen(stage, Integer.parseInt(chosen.split(" ")[0]));
            } catch (SQLException ex) {
                message.setText("Database problem: " + ex.getMessage());
            }
        });

        VBox root = new VBox(12, title, prompt, customers, open, message);
        root.setPadding(new Insets(24));
        stage.setTitle("Banking System - Staff");
        stage.setScene(new Scene(root, 460, 300));
        stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    /** Loads the customer and their accounts from the database and shows the account screen. */
    private void showAccountScreen(Stage stage, int customerId) throws SQLException {
        Customer customer = customerDao.findById(customerId);
        accountDao.findByCustomer(customerId, customer);

        AccountView accountView = new AccountView();
        new AccountController(customer, accountView);
        stage.setTitle("Banking System - " + customer.getFullName());
        stage.setScene(new Scene(accountView.getRoot(), 460, 560));
        stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }
}