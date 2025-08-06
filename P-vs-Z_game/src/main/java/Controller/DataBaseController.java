package Controller;
import Model.*;
import java.sql.ResultSet;

public class DataBaseController {
    private static DataBaseController instance;
    private DataBase dataBase = DataBase.getInstance();

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
        boolean res = dataBase.ExecuteSQL(sqlCmd);
        if (res) {
            return "Success";
        }
        return "Fail";
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

    public ResultSet getPlayerByUsername(String username) {
        if (!username.matches("[a-zA-Z0-9_]+")) {
            return null;
        }

        String sql = "SELECT * FROM player WHERE username = '" + username + "'";
        return dataBase.ExecuteQuery(sql);
    }

    public ResultSet getPlayersSortedByScore() {
        String sql = "SELECT username, score FROM player ORDER BY score DESC";
        return dataBase.ExecuteQuery(sql);
    }
}