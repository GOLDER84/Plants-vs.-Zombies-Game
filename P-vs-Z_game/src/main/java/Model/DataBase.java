package Model;
import java.sql.*;

public class DataBase {
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