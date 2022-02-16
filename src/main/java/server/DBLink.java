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
    // 1 = user exists
    // 2 = user does not exist
    public String getUser(String username) {
        Statement = conn.createStatement();
        String sql = String.format("SELECT * FROM users WHERE username = '%s'", username);
        ResultSet rs = s.executeQuery(sql);
        return rs.next() ? 0 : 1
    }

    // 0 = user deleted
    // 1 = user does not exist
    public String delUser(String username) {
        if (getUser(username).equals(0)) {
            Statement s = conn.createStatement();
            String sql = String.format("DELETE FROM users WHERE username = '%s'", username);
            s.executeUpdate(sql);
            return 0;
        }
        return 1;
    }

}