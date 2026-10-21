package bank;

import bank.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankApp extends Application {

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        loginView.setOnLogin(() ->
                loginView.showError("Login is not connected yet."));

        stage.setTitle("Banking System");
        stage.setScene(new Scene(loginView.getRoot(), 380, 360));
        stage.show();
    }
}