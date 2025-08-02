package Model;
import java.sql.*;

public class DataBase {
    //        String URL = "jdbc:mysql://localhost:3306/game";
//        String userName = "root";
//        String password = "1234";
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con = DriverManager.getConnection(URL, userName, password);
//        System.out.println("Connected to the database successfully!");
//
//        String sql = "INSERT INTO `player` (`ID`, `username`, `password`, `openedPlants`, `currentStage`) VALUES ('1', 'xfb', 'dsgdf', 'part', '2');";
//        Statement statement = con.createStatement();
//        statement.executeUpdate(sql);
//        con.close();
//        System.out.println("Finished inserting data into the database!");

    private static DataBase instance;

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    String URL = "jdbc:mysql://localhost:3306/game";
    String userName = "root";
    String password = "1234";

    public Boolean ExecuteSQL(String sqlCmd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, userName, password);
            Statement s = con.prepareStatement(sqlCmd);
            s.executeUpdate(sqlCmd);
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet ExecuteQuery(String sqlCmd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, userName, password);

            Statement s = con.prepareStatement(sqlCmd);
            ResultSet rs = s.executeQuery(sqlCmd);

            return rs;
        } catch (Exception e) {
            return null;
        }
    }
}