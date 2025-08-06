package org.example.pvsz_game;

import Controller.DataBaseController;
import Controller.PlayerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    private Stage stage;
    PlayerController playerController = PlayerController.getInstance();
    DataBaseController dataBaseController = DataBaseController.getInstance();

    @FXML
    private ImageView ADVButton;

    @FXML
    private Label playerName;

    @FXML
    private ImageView MORButton;

    @FXML
    private ImageView propectice;

    @FXML
    private ImageView settingsBtn;

    @FXML
    private Button achievementsBtn;

    @FXML
    private Button changeBtn;

    @FXML
    private Button leaderboardsBtn;

    @FXML
    private Label level;

    @FXML
    private Label dayOrNight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fxmlPath;
        if (playerController.lastSignedUpPlayer.getCurrentStage() < 4) {
            fxmlPath = "stepsDay.fxml";
        } else {
            fxmlPath = "stepsNight.fxml";
        }
        ADVButton.setOnMouseClicked((MouseEvent event) -> {
            try {
                this.stage = HelloApplication.primaryStage;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        playerName.setText(dataBaseController.getUsernameByID(playerController.lastSignedUpPlayer.getId()));
        level.setText(playerController.lastSignedUpPlayer.getCurrentStage() + "");
        dayOrNight.setText(playerController.lastSignedUpPlayer.getCurrentStage() < 4 ? "Day" : "Night");

    }

    @FXML
    void dragDetected(MouseEvent event) {
        ADVButton.setOpacity(1);
    }

    @FXML
    void dragDetected2(MouseEvent event) {
        ADVButton.setOpacity(0);
    }


    @FXML
    void MORDetected(MouseEvent event) {
        MORButton.setOpacity(1);
    }

    @FXML
    void MORDetected2(MouseEvent event) {
        MORButton.setOpacity(0);
    }

    @FXML
    void achievementsClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("info.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeClicked(MouseEvent event) throws IOException{
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userManagement.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }

    @FXML
    void leaderboardsClicked(MouseEvent event) throws IOException{
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }

    @FXML
    void stnClicked(MouseEvent event) {
        if (propectice.getOpacity() == 0) {
            propectice.setOpacity(1);
        } else {
            propectice.setOpacity(0);
        }
    }

    @FXML
    void settingsBtn(MouseEvent event) throws IOException{
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }
}
