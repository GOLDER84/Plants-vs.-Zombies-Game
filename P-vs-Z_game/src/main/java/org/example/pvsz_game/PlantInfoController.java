package org.example.pvsz_game;

import Controller.PlayerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlantInfoController implements Initializable {
    private Stage stage;
    private PlayerController playerController = PlayerController.getInstance();

    @FXML
    private ImageView em1;

    @FXML
    private ImageView em10;

    @FXML
    private ImageView em2;

    @FXML
    private ImageView em3;

    @FXML
    private ImageView em4;

    @FXML
    private ImageView em5;

    @FXML
    private ImageView em6;

    @FXML
    private ImageView em7;

    @FXML
    private ImageView em8;

    @FXML
    private ImageView em9;

    @FXML
    private ImageView plantInfoGif;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plantInfoGif.setImage(new Image("file:src/main/resources/Gifs/SunflowerInfo.gif"));
        int currentStage = playerController.lastSignedUpPlayer.getCurrentStage();
        if (currentStage == 2) {
            em1.setOpacity(0);
            em1.setDisable(true);
            em2.setOpacity(0);
            em2.setDisable(true);
        } else if (currentStage == 3) {
            em1.setOpacity(0);
            em2.setOpacity(0);
            em3.setOpacity(0);
            em4.setOpacity(0);
            em1.setDisable(true);
            em2.setDisable(true);
            em3.setDisable(true);
            em4.setDisable(true);
        } else if (currentStage == 4) {
            em1.setOpacity(0);
            em2.setOpacity(0);
            em3.setOpacity(0);
            em4.setOpacity(0);
            em5.setOpacity(0);
            em6.setOpacity(0);
            em1.setDisable(true);
            em2.setDisable(true);
            em3.setDisable(true);
            em4.setDisable(true);
            em5.setDisable(true);
            em6.setDisable(true);
        } else if (currentStage == 5) {
            em1.setOpacity(0);
            em2.setOpacity(0);
            em3.setOpacity(0);
            em4.setOpacity(0);
            em5.setOpacity(0);
            em6.setOpacity(0);
            em7.setOpacity(0);
            em8.setOpacity(0);
            em1.setDisable(true);
            em2.setDisable(true);
            em3.setDisable(true);
            em4.setDisable(true);
            em5.setDisable(true);
            em6.setDisable(true);
            em7.setDisable(true);
            em8.setDisable(true);
        } else if (currentStage == 6) {
            em1.setOpacity(0);
            em2.setOpacity(0);
            em3.setOpacity(0);
            em4.setOpacity(0);
            em5.setOpacity(0);
            em6.setOpacity(0);
            em7.setOpacity(0);
            em8.setOpacity(0);
            em9.setOpacity(0);
            em10.setOpacity(0);
            em1.setDisable(true);
            em2.setDisable(true);
            em3.setDisable(true);
            em4.setDisable(true);
            em5.setDisable(true);
            em6.setDisable(true);
            em7.setDisable(true);
            em8.setDisable(true);
            em9.setDisable(true);
            em10.setDisable(true);
        }
    }

    @FXML
    void backToInfoClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cherrybombClicked(MouseEvent event) {
        plantInfoGif.setImage(new Image("file:src/main/resources/Gifs/CherryBombInfo.gif"));
    }

    @FXML
    void closeClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void doomshroomClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/DoomShroomInfo.gif")));
    }

    @FXML
    void peashooterClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/PeashooterInfo.gif")));
    }

    @FXML
    void puffShroomClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/PuffShroomInfo.gif")));
    }

    @FXML
    void repeaterClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/RepeaterInfo.gif")));
    }

    @FXML
    void scarelyshroomClicked(MouseEvent event) {
        plantInfoGif.setImage(new Image(("file:src/main/resources/Gifs/ScaredyShroomInfo.gif")));
    }

    @FXML
    void snowpeaClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/SnowPeaInfo.gif")));
    }

    @FXML
    void sunflowerClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/SunflowerInfo.gif")));
    }

    @FXML
    void sunshroomClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/SunShroomInfo.gif")));
    }

    @FXML
    void wallnutClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/WallNutInfo.gif")));
    }

    @FXML
    void IceShroomClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/IceShroomInfo.gif")));
    }

    @FXML
    void fumeShroomClicked(MouseEvent event) {
        plantInfoGif.setImage((new Image("file:src/main/resources/Gifs/FumeShroomInfo.gif")));
    }

}
