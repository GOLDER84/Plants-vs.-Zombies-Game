package org.example.pvsz_game;

import Controller.DataBaseController;
import Controller.PlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    DataBaseController dataBaseController = DataBaseController.getInstance();
    static Stage primaryStage;
    public static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) {
        String videoPath = new File("src/main/resources/videos/intro.mp4").toURI().toString();

        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        StackPane root = new StackPane(mediaView);
        Scene introScene = new Scene(root , 946 , 638);

        mediaView.fitWidthProperty().bind(introScene.widthProperty());
        mediaView.fitHeightProperty().bind(introScene.heightProperty());
        mediaView.setPreserveRatio(false);

        stage.setScene(introScene);
        stage.setTitle("Intro Video");
        stage.show();

        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                startMainGame(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void startMainGame(Stage stage) throws IOException, SQLException {
        String path = "src/main/resources/musics/02. Crazy Dave (Intro Theme).mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        playMusic();
        System.out.println(dataBaseController.existPlayer("Aref"));

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logo.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public static void playMusic() {
        mediaPlayer.play();
    }
    public static void pauseMusic() {
        mediaPlayer.pause();
    }
    public static void main(String[] args) {
        launch();
    }
}