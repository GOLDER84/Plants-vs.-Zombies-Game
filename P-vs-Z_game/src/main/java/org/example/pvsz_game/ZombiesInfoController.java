package org.example.pvsz_game;

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

public class ZombiesInfoController implements Initializable {
    private Stage stage;

    @FXML
    private ImageView zombieInfoGif;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zombieInfoGif.setImage((new Image("file:src/main/resources/Gifs/NormalZombieInfo.gif")));
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
    void closeClicked(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void coneheadZombieClicked(MouseEvent event) {
        zombieInfoGif.setImage(new Image("file:src/main/resources/Gifs/ConeheadZombieInfo.gif"));
    }

    @FXML
    void flagZombieClicked(MouseEvent event) {
        zombieInfoGif.setImage(new Image("file:src/main/resources/Gifs/FlagZombieInfo.gif"));
    }

    @FXML
    void newspaperZombieClicked(MouseEvent event) {
        zombieInfoGif.setImage(new Image("file:src/main/resources/Gifs/NewspaperZombieInfo.gif"));
    }

    @FXML
    void normalZombieClicked(MouseEvent event) {
        zombieInfoGif.setImage((new Image("file:src/main/resources/Gifs/NormalZombieInfo.gif")));
    }

    @FXML
    void screenDoorClicked(MouseEvent event) {
        zombieInfoGif.setImage((new Image("file:src/main/resources/Gifs/ScreenDoorZombieInfo.gif")));
    }

}
