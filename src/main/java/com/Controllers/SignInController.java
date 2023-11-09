package com.Controllers;

import com.Utilities.Messages;
import com.Utilities.Resources;
import com.Utilities.UserManager;
import com.world_rally_cross_championship.WorldRallyCrossChampionship;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {

    public TextField passwordField;
    public TextField usernameField;
    public Button signInBtn;
    public Button signUpBtn;
    public Label title;

    public void initialize() {

    }

    public void onSignIn(ActionEvent actionEvent) throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("")) {
            Messages.error("Invalid Credentials", "Username cannot be empty, please try again!");
            return;
        }

        if (password.equals("")) {
            Messages.error("Invalid Credentials", "Password cannot be empty, please try again!");
            return;
        }

        if (signInBtn.getText().equals("Sign In")) {
            if (UserManager.signIn(username, password)) {
                navigateToHome(actionEvent);
            } else {
                Messages.error("Sign in failed", "Invalid credentials, please try again!");
            }
        } else {
            if (UserManager.signUp(username, password)) {
                navigateToHome(actionEvent);
            } else {
                Messages.error("Sign up failed", "You have already have an account!");
            }
        }
    }

    public void navigateToHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader homeScreenParent = Resources.getScreen("home.fxml");
        Scene homeScreenScene = new Scene(homeScreenParent.load());
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(homeScreenScene);
        currentStage.show();
    }

    public void onSignUp(ActionEvent actionEvent) {
        if (signUpBtn.getText().equals("Sign Up")) {
            signUpBtn.setText("Sign In");
            signInBtn.setText("Sign Up");
            title.setText("Sign Up");
        }
        else {
            signUpBtn.setText("Sign Up");
            signInBtn.setText("Sign In");
            title.setText("Sign In");
        }
    }
}