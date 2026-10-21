package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/** Boundary class: shows the login screen and collects input. No business logic here. */
public class LoginView {

    private final VBox root = new VBox(12);
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Login");
    private final Label messageLabel = new Label();

    public LoginView() {
        Label title = new Label("Banking System");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label subtitle = new Label("Staff Login");

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        messageLabel.setStyle("-fx-text-fill: red;");

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, subtitle, usernameField,
                passwordField, loginButton, messageLabel);
    }

    public VBox getRoot() { return root; }

    public String getUsername() { return usernameField.getText(); }

    public String getPassword() { return passwordField.getText(); }

    public void setOnLogin(Runnable action) {
        loginButton.setOnAction(e -> action.run());
    }

    public void showError(String message) { messageLabel.setText(message); }

    public void clear() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }
}