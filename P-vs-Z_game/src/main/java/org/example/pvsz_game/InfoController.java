package org.example.pvsz_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoController {
    private Stage stage;

    @FXML
    void closeBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewPlantsBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plant.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewZombieBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zombiesInfo.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
