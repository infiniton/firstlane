package server.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.json.JSONObject;

public class DBLink {
    private static final String url = "jdbc:mysql://localhost:3306/firstlane";
    private static final String username = "firstlane";
    private static final String password = "lastdash";
    private static Connection conn = null;

    public DBLink() {
        try {
            // load driver
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // username in
    // 0 = user exists
    // 1 = user does not
    // 2 = error
    public int findUser(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            return rs.next() ? 0 : 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 2;
    }

    // add user to database
    // 0 = success
    // 2 = error
    public int addUser(String username, String password) {
        try (PreparedStatement s = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            s.setString(1, username);
            s.setString(2, password);
            s.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    public String getPassword(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
