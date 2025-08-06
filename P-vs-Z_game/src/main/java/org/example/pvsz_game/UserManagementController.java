package org.example.pvsz_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Controller.PlayerController;
import javafx.stage.Stage;

public class UserManagementController implements Initializable {
    private PlayerController playerController = PlayerController.getInstance();
    private Stage stage;
    @FXML
    private Label playerName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerName.setText(playerController.lastSignedUpPlayer.getUsername());
    }
    @FXML
    void cancelBtn(MouseEvent event) throws IOException {
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
    void changePasswordBtn(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your new password:");
        dialog.setContentText("New Password:");
        dialog.showAndWait().ifPresent(newPassword -> {
            String result = playerController.changePassword(newPassword);
            if (result.equals("Password changed successfully")) {
                new Alert(Alert.AlertType.INFORMATION, result, ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, result, ButtonType.OK).showAndWait();
            }
        });
    }

    @FXML
    void deleteUsernameBtn(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String result = playerController.deletePlayer();
                if (result.equals("Player deleted successfully")) {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Delete Account");
                    alert2.setContentText(result);
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
                    new Alert(Alert.AlertType.ERROR, result, ButtonType.OK).showAndWait();
                }
            }
        });
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
    void renameUsernameBtn(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename Username");
        dialog.setHeaderText("Enter your new username:");
        dialog.setContentText("New Username:");
        dialog.showAndWait().ifPresent(newUsername -> {
            String result = playerController.renamePlayer(newUsername);
            if (result.equals("Username changed successfully")) {
                playerName.setText(newUsername);
                new Alert(Alert.AlertType.INFORMATION, result, ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, result, ButtonType.OK).showAndWait();
            }
        });
    }
}
