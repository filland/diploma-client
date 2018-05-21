package bntu.diploma.controller;

import bntu.diploma.classes.WeatherAPIWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    /**
     *
     *
     * THE WAY TO SPLIT CONTROLLERS
     *
     *
     * https://stackoverflow.com/questions/15041760/javafx-open-new-window
     *
     *
     **/

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField IDField;
    @FXML
    private PasswordField secretKeyField;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox asAdminCheckbox;
    @FXML
    private Hyperlink needHelpLink;
    @FXML
    private Label errorLabel;


    private WeatherAPIWorker weatherAPIWorker;

    @FXML
    public void initialize(){

        asAdminCheckbox.setDisable(true);
        weatherAPIWorker = WeatherAPIWorker.getInstance();

        IDField.setText(String.valueOf(1));
        secretKeyField.setText("666");
    }

    public void loginButtonPressed(ActionEvent actionEvent) {

        boolean availableServer = weatherAPIWorker.isAvailableServer();

        if (!availableServer){
            IDField.setText("Сервер не отвечает");
            return;
        }


        if (IDField.getText().trim().isEmpty() || secretKeyField.getText().trim().isEmpty()) {

            errorLabel.setVisible(true);
            errorLabel.setText("ID field or Secret key field is empty");

        } else {

            errorLabel.setText("");

            boolean isLoggedIn = weatherAPIWorker.login(IDField.getText(), secretKeyField.getText());


            if (isLoggedIn) {

                Parent root;
                try {
                    Stage stage = new Stage();

                    root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
                    stage.setTitle("WeatherTower");
                    stage.setScene(new Scene(root));

                    stage.setMinWidth(1100);
                    stage.setMinHeight(650);
                    stage.show();

                    // Hide this current window (if this is what you want)
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An error while closing Login Frame and opening MainFrame");
                }

            }

        }


    }

    public void needHelpLinkClicked(ActionEvent actionEvent) {
        IDField.setText("needHelpLinkClicked");
    }


}
