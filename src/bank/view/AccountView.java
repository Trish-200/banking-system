package bank.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/** Boundary class: shows account details and collects input. No business logic here. */
public class AccountView {

    private final VBox root = new VBox(12);
    private final ComboBox<String> accountSelector = new ComboBox<>();
    private final Label balanceLabel = new Label("Balance: BWP 0.00");
    private final TextField amountField = new TextField();
    private final Button depositButton = new Button("Deposit");
    private final Button withdrawButton = new Button("Withdraw");
    private final ListView<String> historyList = new ListView<>();
    private final Label messageLabel = new Label();

    public AccountView() {
        Label title = new Label("Account Services");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        accountSelector.setPromptText("Select an account");
        accountSelector.setMaxWidth(Double.MAX_VALUE);
        balanceLabel.setStyle("-fx-font-size: 16px;");
        amountField.setPromptText("Amount (BWP)");

        HBox buttons = new HBox(10, depositButton, withdrawButton);
        Label historyTitle = new Label("Transaction history");
        historyList.setPrefHeight(180);

        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, accountSelector, balanceLabel,
                amountField, buttons, messageLabel, historyTitle, historyList);
    }

    public VBox getRoot() { return root; }

    public void setAccounts(List<String> accounts) {
        accountSelector.getItems().setAll(accounts);
    }

    public String getSelectedAccount() { return accountSelector.getValue(); }

    public String getAmountText() { return amountField.getText(); }

    public void clearAmount() { amountField.clear(); }

    public void setBalance(String text) { balanceLabel.setText("Balance: " + text); }

    public void setHistory(List<String> lines) { historyList.getItems().setAll(lines); }

    public void showMessage(String message, boolean isError) {
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    public void setOnAccountChanged(Runnable action) {
        accountSelector.setOnAction(e -> action.run());
    }

    public void setOnDeposit(Runnable action) {
        depositButton.setOnAction(e -> action.run());
    }

    public void setOnWithdraw(Runnable action) {
        withdrawButton.setOnAction(e -> action.run());
    }
}