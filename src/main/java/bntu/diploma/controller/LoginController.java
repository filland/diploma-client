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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    /**
     * THE WAY TO SPLIT CONTROLLERS
     * <p>
     * <p>
     * https://stackoverflow.com/questions/15041760/javafx-open-new-window
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
    private Label messageLabel;


    private WeatherAPIWorker weatherAPIWorker;

    @FXML
    public void initialize() {

        asAdminCheckbox.setDisable(true);
        weatherAPIWorker = WeatherAPIWorker.getInstance();

        IDField.setText(String.valueOf(1));
        secretKeyField.setText("6666666666");
    }

    public void loginButtonPressed(ActionEvent actionEvent) {

        boolean availableServer = weatherAPIWorker.isServerAvailable();

        if (!availableServer) {
            messageLabel.setVisible(true);
            messageLabel.setText("Сервер не отвечает");
            return;
        }


        if (IDField.getText().trim().isEmpty() || secretKeyField.getText().trim().isEmpty()) {

            messageLabel.setVisible(true);
            messageLabel.setText("ID field or Secret key field is empty");

        } else {

            messageLabel.setText("");
            messageLabel.setTextFill(Color.BLACK);

            System.out.println("about to login");

            boolean isLoggedIn = weatherAPIWorker.login(IDField.getText(), secretKeyField.getText());

            System.out.println("login result - " + isLoggedIn);

            if (isLoggedIn) {

                Parent root;
                try {
                    Stage stage = new Stage();

                    root = FXMLLoader.load(getClass().getResource("/fxml/mainFrame.fxml"));
                    stage.setTitle("WeatherTower");
                    stage.setScene(new Scene(root));

                    stage.setMinWidth(1000);
                    stage.setMinHeight(500);

                    stage.setWidth(1200);
                    stage.setHeight(650);
                    stage.show();

                    // Hide this current window (if this is what you want)
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An error while closing Login Frame and opening MainFrame");
                }

            } else {

                messageLabel.setVisible(true);
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Не удалось авторизоваться");

            }

        }


    }

    public void needHelpLinkClicked(ActionEvent actionEvent) {
        IDField.setText("needHelpLinkClicked");
    }


}
