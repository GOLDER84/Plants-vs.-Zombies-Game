package Model;

import Controller.DataBaseController;

import java.util.ArrayList;
import java.util.List;

public class Player {
    DataBaseController dataBaseController = DataBaseController.getInstance();
    private final int id;
    private String username;
    private String password;
    private ArrayList<Plant> openedPlants;;
    private int currentStage;
    private int score;

    public Player(String username, String password, ArrayList<Plant> openedPlants) {
        this.id = dataBaseController.getMaxID()+ 1;
        this.username = username;
        this.password = password;
        this.openedPlants = openedPlants;
        this.currentStage = 1;
        this.score = 0;
    }
    public Player(int id, String username, String password, ArrayList<Plant> openedPlants, int currentStage , int score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.openedPlants = openedPlants;
        this.currentStage = currentStage;
        this.score = score;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Plant> getOpenedPlants() {
        return openedPlants;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOpenedPlants(ArrayList<Plant> openedPlants) {
        this.openedPlants = openedPlants;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
