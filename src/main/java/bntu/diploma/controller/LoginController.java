package bntu.diploma.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox asAdminCheckbox;
    @FXML
    private Hyperlink needHelpLink;


    public void loginButtonPressed(ActionEvent actionEvent) {

        if(asAdminCheckbox.isSelected()){

            usernameField.setText("login as admin");

        } else {

            usernameField.setText("login as user");
        }

    }

    public void needHelpLinkClicked(ActionEvent actionEvent) {
        usernameField.setText("needHelpLinkClicked");
    }
}

