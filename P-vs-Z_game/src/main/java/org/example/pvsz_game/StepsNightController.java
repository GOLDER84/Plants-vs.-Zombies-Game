package org.example.pvsz_game;

import Controller.PlayerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class StepsNightController {
    private Stage stage;
    PlayerController playerController = PlayerController.getInstance();
    @FXML
    private Button backBtn;

    @FXML
    private Button dayBtn;

    @FXML
    private ImageView dayStep;

    @FXML
    private Button play4Btn;

    @FXML
    private Button play5Btn;

    @FXML
    private Button play6Btn;

    @FXML
    void backClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void dayClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsDay.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void play4Clicked(MouseEvent event) throws IOException{
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the previous step.");
            alert.showAndWait();
        } else {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage4.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void play5Clicked(MouseEvent event) throws IOException{
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the previous step.");
            alert.showAndWait();
        } else {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage6.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void play6Clicked(MouseEvent event) throws IOException{
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the previous step.");
            alert.showAndWait();
        } else {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage5.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}
