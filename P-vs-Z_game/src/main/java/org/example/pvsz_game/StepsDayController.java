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

public class StepsDayController {
    private Stage stage;
    PlayerController playerController = PlayerController.getInstance();

    @FXML
    private Button backBtn;

    @FXML
    private ImageView dayStep;

    @FXML
    private Button nightBtn;

    @FXML
    private Button play1Btn;

    @FXML
    private Button play2Btn;

    @FXML
    private Button play3Btn;

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
    void nightClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsNight.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void play1Clicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage1.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void play2Clicked(MouseEvent event) throws IOException {
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 2) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Information");
          alert.setHeaderText(null);
          alert.setContentText("Please complete the previous step.");
          alert.showAndWait();
        } else {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage2.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void play3Clicked(MouseEvent event) throws IOException{
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the previous step.");
            alert.showAndWait();
        } else {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stage3.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}
