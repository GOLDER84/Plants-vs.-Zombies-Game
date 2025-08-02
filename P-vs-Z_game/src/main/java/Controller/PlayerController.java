package Controller;

import Model.*;
import Model.Conehead_Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {
    private DataBaseController dataBaseController = DataBaseController.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private static PlayerController instance;
    public Player lastSignedUpPlayer;
    public PlayerController() {

    }
    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }
    public String createPlayer(String username, String password) {
        String validation = checkSignupInputs(username, password);
        if (!validation.equals("Valid")) {
            return validation;
        }
        if (dataBaseController.existPlayer(username)) {
            return "Username already exists";
        }
        Player newPlayer = new Player(username , password , new ArrayList<>(List.of(new Peashooter() , new Sunflower())));
        lastSignedUpPlayer = newPlayer;
        ArrayList<String> op = new ArrayList<>();
        for (Plant p : newPlayer.getOpenedPlants()){
            op.add(p.getPlantName());
        }
        System.out.println(dataBaseController.insertPlayer(newPlayer.getId() , username , password , op.toString()));
        return "Account created successfully";
    }

    public String checkSignupInputs(String username, String password) {
        if (username == null || username.isEmpty()) return "Username cannot be empty";
        if (password == null || password.isEmpty()) return "Password cannot be empty";
        if (!checkPasswordStrength(password)) return "Password is too weak";
        return "Valid";
    }

    public boolean checkPasswordStrength(String password) {
        return password.length() >= 6 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }

    public Player loadPlayerByUsername(String username) {
        ResultSet rs = dataBaseController.getPlayerByUsername(username);
        try {
            if (rs != null && rs.next()) {
                int id = rs.getInt("ID");
                String password = rs.getString("password");
                String openedPlantsStr = rs.getString("openedPlants"); // مثال: [Peashooter, Sunflower]
                int currentStage = rs.getInt("currentStage");
                int score = rs.getInt("score");

                ArrayList<Plant> openedPlants = new ArrayList<>();
                String cleaned = openedPlantsStr.replace("[", "").replace("]", "").replace(" ", "");
                String[] plantNames = cleaned.split(",");
                for (String name : plantNames) {
                    switch (name) {
                        case "Peashooter" -> openedPlants.add(new Peashooter());
                        case "sunflower" -> openedPlants.add(new Sunflower());
                        case "Cherry_bomb" -> openedPlants.add(new Cherry_bomb());
                        case "Wall_nut" -> openedPlants.add(new Wall_nut());
                        case "Snow_pea" -> openedPlants.add(new Snow_pea());
                        case "Repeater" -> openedPlants.add(new Repeater());
                        case "Puff_shroom" -> openedPlants.add(new Puff_shroom());
                        case "Sun_shroom" -> openedPlants.add(new Sun_shroom());
                        case "Fume_shroom" -> openedPlants.add(new Fume_shroom());
                        case "Scaredy_shroom" -> openedPlants.add(new Scaredy_shroom());
                        case "Ice_shroom" -> openedPlants.add(new Ice_shroom());
                        case "Doom_shroom" -> openedPlants.add(new Doom_shroom());
                    }
                }
                return new Player(id, username, password, openedPlants, currentStage , score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String signIn(String username, String password) {

        if (username == null || username.isEmpty()) return "Username cannot be empty";
        if (password == null || password.isEmpty()) return "Password cannot be empty";

        if (!dataBaseController.existPlayer(username)) {
            return "Username does not exist";
        }

        Player player = loadPlayerByUsername(username);
        if (player == null) {
            return "Error loading player data";
        }

        if (!player.getPassword().equals(password)) {
            return "Incorrect password";
        }

        lastSignedUpPlayer = player;
        return "Signed in successfully";
    }

    public String logout() {
        lastSignedUpPlayer = null;
        return "Logged out successfully";
    }

    public String changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) return "New password cannot be empty";
        if (!checkPasswordStrength(newPassword)) return "New password is too weak";

        String sql = "UPDATE `player` SET `password` = '" + newPassword + "' WHERE `player`.`ID` = " + lastSignedUpPlayer.getId();
        boolean result = dataBase.ExecuteSQL(sql);
        if (result) {
            lastSignedUpPlayer.setPassword(newPassword);
            return "Password changed successfully";
        } else {
            return "Failed to change password";
        }
    }

    public String deletePlayer() {
        if (lastSignedUpPlayer == null) return "No player is currently signed in";

        String sql = "DELETE FROM `player` WHERE `ID` = " + lastSignedUpPlayer.getId();
        boolean result = dataBase.ExecuteSQL(sql);
        if (result) {
            lastSignedUpPlayer = null;
            return "Player deleted successfully";
        } else {
            return "Failed to delete player";
        }
    }

    public String renamePlayer(String newUsername) {
        if (newUsername == null || newUsername.isEmpty()) return "New username cannot be empty";
        if (dataBaseController.existPlayer(newUsername)) return "Username already exists";

        String sql = "UPDATE `player` SET `username` = '" + newUsername + "' WHERE `player`.`ID` = " + lastSignedUpPlayer.getId();
        boolean result = dataBase.ExecuteSQL(sql);
        if (result) {
            lastSignedUpPlayer.setUsername(newUsername);
            return "Username changed successfully";
        } else {
            return "Failed to change username";
        }
    }

    public String increaseScore(int score) {
        String sql = "UPDATE `player` SET `score` = '"+ lastSignedUpPlayer.getScore()+score +"' WHERE `player`.`ID` = " + lastSignedUpPlayer.getId();
        boolean result = dataBase.ExecuteSQL(sql);
        if (result) {
            lastSignedUpPlayer.setScore(lastSignedUpPlayer.getScore()+score);
            return "Score changed successfully";
        } else {
            return "Failed to increase score";
        }
    }
    public String decreaseScore(int score) {
        String sql = "UPDATE `player` SET `score` = '"+ (lastSignedUpPlayer.getScore()-score) +"' WHERE `player`.`ID` = " + lastSignedUpPlayer.getId();
        boolean result = dataBase.ExecuteSQL(sql);
        if (result) {
            lastSignedUpPlayer.setScore(lastSignedUpPlayer.getScore()-score);
            return "Score decreased successfully";
        } else {
            return "Failed to decrease score";
        }
    }

    public String updateCurrentStage(int currentStage , int ID){
        String sql = "UPDATE `player` SET `currentStage` = '" + currentStage + "' WHERE `player`.`ID` = " + ID;
        lastSignedUpPlayer.setCurrentStage(currentStage);
        if (currentStage == 2){
            ArrayList<String> op = new ArrayList<>();
            for (Plant p : lastSignedUpPlayer.getOpenedPlants()){
                op.add(p.getPlantName());
            }
            op.add("Cherry_bomb");
            op.add("Wall_nut");
            String sql2 = "UPDATE `player` SET `openedPlants` = '"+op.toString()+"' WHERE `player`.`ID` = " + ID;
            boolean res = dataBase.ExecuteSQL(sql2);
            if (res){
                boolean rs = dataBase.ExecuteSQL(sql);
                if (rs){
                    return "Current stage updated and success to plant";
                }
            } else {
                return "Fail to plant";
            }
        }

        if (currentStage == 3){
            ArrayList<String> op = new ArrayList<>();
            for (Plant p : lastSignedUpPlayer.getOpenedPlants()){
                op.add(p.getPlantName());
            }
            op.add("Snow_pea");
            op.add("Repeater");
            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
            boolean res = dataBase.ExecuteSQL(sql2);
            if (res){
                boolean rs = dataBase.ExecuteSQL(sql);
                if (rs){
                    return "Current stage updated and success to plant";
                }
            } else {
                return "Fail to plant";
            }
        }

        if (currentStage == 4){
            ArrayList<String> op = new ArrayList<>();
            for (Plant p : lastSignedUpPlayer.getOpenedPlants()){
                op.add(p.getPlantName());
            }
            op.add("Puff_shroom");
            op.add("Sun_shroom");
            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
            boolean res = dataBase.ExecuteSQL(sql2);
            if (res){
                boolean rs = dataBase.ExecuteSQL(sql);
                if (rs){
                    return "Current stage updated and success to plant";
                }
            } else {
                return "Fail to plant";
            }
        }

        if (currentStage == 5){
            ArrayList<String> op = new ArrayList<>();
            for (Plant p : lastSignedUpPlayer.getOpenedPlants()){
                op.add(p.getPlantName());
            }
            op.add("Fume_shroom");
            op.add("Scaredy_shroom");
            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
            boolean res = dataBase.ExecuteSQL(sql2);
            if (res){
                boolean rs = dataBase.ExecuteSQL(sql);
                if (rs){
                    return "Current stage updated and success to plant";
                }
            } else {
                return "Fail to plant";
            }
        }

        if (currentStage == 6){
            ArrayList<String> op = new ArrayList<>();
            for (Plant p : lastSignedUpPlayer.getOpenedPlants()){
                op.add(p.getPlantName());
            }
            op.add("Ice_shroom");
            op.add("Doom_shroom");
            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
            boolean res = dataBase.ExecuteSQL(sql2);
            if (res){
                boolean rs = dataBase.ExecuteSQL(sql);
                if (rs){
                    return "Current stage updated and success to plant";
                }
            } else {
                return "Fail to plant";
            }
        }
        return "Fail to plant";
    }
    

}
