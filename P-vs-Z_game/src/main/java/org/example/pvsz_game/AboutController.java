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
import javafx.scene.control.Label;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private ImageView information;

    @FXML
    private Label cookie;

    @FXML
    private Label userAgreement;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        information.setImage(new Image("file:src/main/resources/images/aboutPng.png"));
        cookie.setDisable(true);
        userAgreement.setDisable(true);
    }

    @FXML
    void aboutBtn(MouseEvent event) {
        information.setImage(new Image("file:src/main/resources/images/aboutPng.png"));
    }

    @FXML
    void cookieBtn(MouseEvent event) {
        information.setImage(new Image("file:src/main/resources/images/cookie.jpg"));
        cookie.setDisable(false);
        cookie.setVisible(true);
        cookie.setOnMouseClicked(mouseEvent -> {
            try {
                URI uri = new URI("https://tos.ea.com/legalapp/WEBPRIVACY/US/en/PC/");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(uri);
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void helpBtn(MouseEvent event) {
        try {
            URI uri = new URI("https://www.ea.com/legal/user-agreement?isLocalized=true&setLocale=en-gb");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void okBtn(MouseEvent event) throws IOException {
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(400);
        stage.setY(200);
        stage.show();
    }

    @FXML
    void userBtn(MouseEvent event) {
       information.setImage(new Image("file:src/main/resources/images/user.jpg"));
       userAgreement.setDisable(false);
       userAgreement.setVisible(true);
       userAgreement.setOnMouseClicked(mouseEvent -> {
           try {
               URI uri = new URI("https://www.ea.com/legal/user-agreement?isLocalized=true&setLocale=en-gb");
               if (Desktop.isDesktopSupported()) {
                   Desktop.getDesktop().browse(uri);
               }
           } catch (IOException | URISyntaxException e) {
               e.printStackTrace();
           }
       });
    }

}
