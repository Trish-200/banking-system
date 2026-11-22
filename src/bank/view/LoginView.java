package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;

/** Boundary class: shows the login screen and collects input. No business logic here. */
public class LoginView {

    private final VBox root = new VBox(12);
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField visiblePasswordField = new TextField();
    private final Button eyeButton = new Button();
    private final Line eyeSlash = new Line(2, 13, 20, 1);
    private final Button loginButton = new Button("Login");
    private final Label messageLabel = new Label();

    public LoginView() {
        Label title = new Label("Banking System");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label subtitle = new Label("Bank Login");

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        visiblePasswordField.setPromptText("Password");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        messageLabel.setStyle("-fx-text-fill: red;");

        // The visible field and the dots field always hold the same text.
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());

        // Leave room on the right so the text does not go under the eye.
        passwordField.setStyle("-fx-padding: 8 44 8 8;");
        visiblePasswordField.setStyle("-fx-padding: 8 44 8 8;");

        // Draw the eye: outline, pupil, and a slash that shows while the password is hidden.
        SVGPath outline = new SVGPath();
        outline.setContent("M0 7 C4 0 18 0 22 7 C18 14 4 14 0 7 Z");
        outline.setFill(Color.TRANSPARENT);
        outline.setStroke(Color.web("#5b6b8c"));
        outline.setStrokeWidth(1.8);
        Circle pupil = new Circle(11, 7, 3, Color.web("#5b6b8c"));
        eyeSlash.setStroke(Color.web("#5b6b8c"));
        eyeSlash.setStrokeWidth(2);
        eyeButton.setGraphic(new Group(outline, pupil, eyeSlash));
        eyeButton.setStyle("-fx-background-color: transparent; -fx-padding: 4 8 4 8;");
        eyeButton.setCursor(Cursor.HAND);
        eyeButton.setFocusTraversable(false);

        setShowing(false);
        eyeButton.setOnAction(e -> setShowing(!visiblePasswordField.isVisible()));

        StackPane passwordBox = new StackPane(passwordField, visiblePasswordField, eyeButton);
        StackPane.setAlignment(eyeButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(eyeButton, new Insets(0, 6, 0, 0));

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, subtitle, usernameField,
                passwordBox, loginButton, messageLabel);
    }

    /** Show the typed password as letters (true) or as dots (false). */
    private void setShowing(boolean show) {
        visiblePasswordField.setVisible(show);
        visiblePasswordField.setManaged(show);
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);
        eyeSlash.setVisible(!show);
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
        setShowing(false);
        messageLabel.setText("");
    }
}