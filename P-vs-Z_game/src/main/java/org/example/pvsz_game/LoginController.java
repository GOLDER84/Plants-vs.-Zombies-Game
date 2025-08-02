package org.example.pvsz_game;

import Controller.DataBaseController;
import Controller.PlayerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private PlayerController playerController = PlayerController.getInstance();
//    private DataBaseController dataBaseController = DataBaseController.getInstance();
    private Stage stage;

    @FXML
    private Button okButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void okClicked(MouseEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String result = playerController.signIn(username, password);
        if (result.equals("Signed in successfully")) {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setX(300);
            stage.setY(100);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(result);
            alert.showAndWait();
        }
    }
    @FXML
    void backClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logo.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.show();
    }

}
