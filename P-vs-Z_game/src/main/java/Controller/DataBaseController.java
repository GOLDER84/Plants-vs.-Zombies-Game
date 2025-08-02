package Controller;
import Model.*;
import javafx.scene.image.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    private static DataBaseController instance;
    private DataBase dataBase = DataBase.getInstance();
//    private PlayerController playerController = PlayerController.getInstance();

    public DataBaseController() {
    }

    public static DataBaseController getInstance() {
        if (instance == null) {
            instance = new DataBaseController();
        }
        return instance;
    }

    public String insertPlayer(int ID, String username, String password, String openedPlants) {
        String sqlCmd = String.format(
                "INSERT INTO `player` (`ID`, `username`, `password`, `openedPlants`, `currentStage` , `score`) VALUES ('%d', '%s', '%s', '%s' ,'%d','%d')",
                ID, username,
                password, openedPlants, 1 , 0
        );
//        String sql = "INSERT INTO `player` (`ID`, `username`, `password`, `openedPlants`, `currentStage`) VALUES ('1', 'xfb', 'dsgdf', 'part', '2');";
        boolean res = dataBase.ExecuteSQL(sqlCmd);
        if (res) {
            return "Success";
        }
        return "Fail";
    }

    public ResultSet showAll() {
        ResultSet rs = dataBase.ExecuteQuery("SELECT * FROM `player`");
        return rs;
//                return String.format("ID: %d, username: %s, password: %s, openedPlants: %s, currentStage: %d\n",
//                        rs.getInt("ID"), rs.getString("username"),
//                        rs.getString("password"), rs.getString("openedPlants"),
//                        rs.getInt("currentStage"));
//            while (rs.next()) {
//                System.out.println("");
//                return String.format("ID: %d, username: %s, password: %s, openedPlants: %s, currentStage: %d\n",
//                        rs.getInt("ID"), rs.getString("username"),
//                        rs.getString("password"), rs.getString("openedPlants"),
//                        rs.getInt("currentStage"));
    }

    public boolean existPlayer(String username) {
        if (username == null || username.trim().isEmpty()) return false;

        String sql = "SELECT username FROM player WHERE username = '" + username.trim() + "'";
        ResultSet rs = DataBase.getInstance().ExecuteQuery(sql);
        try {
            return rs != null && rs.next(); // اگر حداقل یک نتیجه اومد یعنی وجود داره
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public int getMaxID() {
        String sql = "SELECT MAX(ID) FROM player";
        ResultSet rs = DataBase.getInstance().ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean isPlayerTableEmpty() {
        String sql = "SELECT COUNT(*) FROM player";
        ResultSet rs = DataBase.getInstance().ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public String getUsernameByID(int ID) {
        if (ID < 0) return null;

        String sql = "SELECT username FROM player WHERE ID = " + ID;
        ResultSet rs = dataBase.ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                return rs.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getCurrentStageByID(int ID) {
        if (ID < 0) return -1;

        String sql = "SELECT currentStage FROM player WHERE ID = " + ID;
        ResultSet rs = dataBase.ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                return rs.getInt("currentStage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String getPasswordByID(int ID) {
        if (ID < 0) return null;

        String sql = "SELECT password FROM player WHERE ID = " + ID;
        ResultSet rs = dataBase.ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getOpenedPlantsByID(int ID) {
        if (ID < 0) return null;

        String sql = "SELECT openedPlants FROM player WHERE ID = " + ID;
        ResultSet rs = dataBase.ExecuteQuery(sql);

        try {
            if (rs != null && rs.next()) {
                return rs.getString("openedPlants");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getPlayerByUsername(String username) {
        if (!username.matches("[a-zA-Z0-9_]+")) {
            return null;
        }

        String sql = "SELECT * FROM player WHERE username = '" + username + "'";
        return dataBase.ExecuteQuery(sql);
    }

//    public String updateCurrentStage(int currentStage , int ID){
//        String sql = "UPDATE `player` SET `currentStage` = '" + currentStage + "' WHERE `player`.`ID` = " + ID;
//        if (currentStage == 2){
//            ArrayList<String> op = new ArrayList<>();
//            for (Plant p : playerController.lastSignedUpPlayer.getOpenedPlants()){
//                op.add(p.getPlantName());
//            }
//            op.add("Cherry_bomb");
//            op.add("Wall_nut");
//            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
//            boolean res = dataBase.ExecuteSQL(sql2);
//            if (res){
//                return "Success to plant";
//            } else {
//                return "Fail to plant";
//            }
//        }
//
//        if (currentStage == 3){
//            ArrayList<String> op = new ArrayList<>();
//            for (Plant p : playerController.lastSignedUpPlayer.getOpenedPlants()){
//                op.add(p.getPlantName());
//            }
//            op.add("Snow_pea");
//            op.add("Repeater");
//            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
//            boolean res = dataBase.ExecuteSQL(sql2);
//            if (res){
//                return "Success to plant";
//            } else {
//                return "Fail to plant";
//            }
//        }
//
//        if (currentStage == 4){
//            ArrayList<String> op = new ArrayList<>();
//            for (Plant p : playerController.lastSignedUpPlayer.getOpenedPlants()){
//                op.add(p.getPlantName());
//            }
//            op.add("Puff_shroom");
//            op.add("Sun_shroom");
//            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
//            boolean res = dataBase.ExecuteSQL(sql2);
//            if (res){
//                return "Success to plant";
//            } else {
//                return "Fail to plant";
//            }
//        }
//
//        if (currentStage == 5){
//            ArrayList<String> op = new ArrayList<>();
//            for (Plant p : playerController.lastSignedUpPlayer.getOpenedPlants()){
//                op.add(p.getPlantName());
//            }
//            op.add("Fume_shroom");
//            op.add("Scaredy_shroom");
//            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
//            boolean res = dataBase.ExecuteSQL(sql2);
//            if (res){
//                return "Success to plant";
//            } else {
//                return "Fail to plant";
//            }
//        }
//
//        if (currentStage == 6){
//            ArrayList<String> op = new ArrayList<>();
//            for (Plant p : playerController.lastSignedUpPlayer.getOpenedPlants()){
//                op.add(p.getPlantName());
//            }
//            op.add("Ice_shroom");
//            op.add("Doom_shroom");
//            String sql2 = "UPDATE `player` SET `openedPlants` = '"+ op.toString() + "' WHERE `player`.`ID` = " + ID;
//            boolean res = dataBase.ExecuteSQL(sql2);
//            if (res){
//                return "Success to plant";
//            } else {
//                return "Fail to plant";
//            }
//        }
//        boolean rs = dataBase.ExecuteSQL(sql);
//
//            if (rs){
//                return "currentStageUpdated";
//            }
//        return "Error";
//    }
    // In src/main/java/Controller/DataBaseController.java

    public ResultSet getPlayersSortedByScore() {
        String sql = "SELECT username, score FROM player ORDER BY score DESC";
        return dataBase.ExecuteQuery(sql);
    }
}