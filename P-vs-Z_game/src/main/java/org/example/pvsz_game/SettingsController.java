package org.example.pvsz_game;

import Controller.PlayerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {

    private Stage stage;
    private PlayerController playerController = PlayerController.getInstance();

    @FXML
    private Slider audioSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        audioSlider.setMin(0);
        audioSlider.setMax(50);
        audioSlider.setValue(50);
        audioSlider.valueProperty().addListener((observable) -> {
            HelloApplication.mediaPlayer.setVolume(audioSlider.getValue() / 100);
        });
    }

    @FXML
    void aboutBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }

    @FXML
    void creditsBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("credits.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }

    @FXML
    void okBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.show();
    }
    @FXML
    void logoutBtn(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String result = playerController.logout();
                if (result.equals("Logged out successfully")) {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Logout");
                    alert2.setContentText(result + " GoodBye!");
                    alert2.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.OK) {
                            try {
                                this.stage = HelloApplication.primaryStage;
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logo.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setX(300);
                                stage.setY(100);
                                stage.show();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK).showAndWait();
                }
            }
        });
    }

}
