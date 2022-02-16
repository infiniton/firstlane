import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class DBLink {
    private static final String url = "jdbc:mysql://localhost:3306/firstlane";
    private static final String username = "firstlane";
    private static final String password = "lastdash";

    public DBLink() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // username in
    // 0 = user exists
    // 1 = user does not 
    // 2 = error
    public int findUser(String username) {
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(String.format("SELECT * FROM users WHERE username = '%s'", username));
            return rs.next() ? 0 : 1 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getLoginInfo(String username){
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(String.format("SELECT "))
        }

    }
}