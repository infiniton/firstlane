package server.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;

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
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE user = ?")) {
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
    public int addUser(String username, String password, String salt, String data) {
        try (PreparedStatement s = conn
                .prepareStatement("INSERT INTO users (user, password, salt, data) VALUES (?, ?, ?, ?)")) {
            s.setString(1, username);
            s.setString(2, password);
            s.setString(3, salt);
            s.setString(4, data);
            s.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    public String getPassword(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE user = ?")) {
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

    public int addPassword(String user, String name, String username, String password, String url, String notes) {
        try (PreparedStatement s = conn
                .prepareStatement("INSERT INTO passwords (uuid, user, name, username, password, url, notes) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            s.setString(1, UUID.randomUUID().toString());
            s.setString(2, user);
            s.setString(3, name);
            s.setString(4, username);
            s.setString(5, password);
            s.setString(6, url);
            s.setString(7, notes);
            s.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

/*        
        // add uuid to user data
        try (PreparedStatement s = conn
                .prepareStatement("UPDATE users SET data = CONCAT(data, ',' , ?) WHERE user = ?")) {
            s.setString(1, UUID.randomUUID().toString());
            s.setString(2, user);
            s.executeUpdate();
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        return 1;
    }

    public String getData(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE user = ?")) {
            s.setString(1, username);
            ResultSet rs = s.executeQuery();

            JSONObject json = new JSONObject();

            while (rs.next()) {
                

                rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("url"), rs.getString("notes")


                json.put(rs.getString("uuid"), valueOf());
                return rs.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getSalt(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE user = ?")) {
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                return rs.getString("salt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
