package org.example.pvsz_game;

import Controller.DataBaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private VBox leaderboardVBox;

    private Stage stage;
    private final DataBaseController dataBaseController = DataBaseController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLeaderboardData();
    }

    private void loadLeaderboardData() {
        ResultSet rs = dataBaseController.getPlayersSortedByScore();
        try {
            int rank = 1;
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");

                HBox entry = new HBox(10);
                entry.setAlignment(Pos.CENTER_LEFT);

                Label rankLabel = new Label(rank + ". " + username);
                rankLabel.setFont(new Font("Arial", 16));
                rankLabel.setTextFill(Color.WHITE);

                Label scoreLabel = new Label(String.valueOf(score));
                scoreLabel.setFont(new Font("Arial", 16));
                scoreLabel.setTextFill(Color.WHITE);

                HBox scoreBox = new HBox(scoreLabel);
                scoreBox.setAlignment(Pos.CENTER_RIGHT);
                HBox.setHgrow(scoreBox, javafx.scene.layout.Priority.ALWAYS);


                entry.getChildren().addAll(rankLabel, scoreBox);
                leaderboardVBox.getChildren().add(entry);
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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