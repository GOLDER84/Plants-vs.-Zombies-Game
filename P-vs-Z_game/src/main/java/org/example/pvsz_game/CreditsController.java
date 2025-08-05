package org.example.pvsz_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {

    @FXML
    private MediaView creditsVideo;

    private Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String videoPath = new File("src/main/resources/videos/credits.mp4").toURI().toString();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        creditsVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                this.stage = HelloApplication.primaryStage;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setX(300);
                stage.setY(100);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    void backClicked(MouseEvent event) throws IOException {
       this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.show();
    }

}
