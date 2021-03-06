package server.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.json.JSONObject;


/***
 * Interface between API and database.
 */
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

    // 0 = success
    // 2 = error
    public int addPassword(JSONObject json, String user) {

        // update data in users db
        try (PreparedStatement s = conn.prepareStatement("UPDATE users SET data = ? WHERE user = ?")) {
            s.setString(1, json.getString("data").toString());
            s.setString(2, user);
            s.executeUpdate();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }

    public String getData(String username) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM users WHERE user = ?")) {
            s.setString(1, username);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {

                if (rs.getString("data").equals(" ")) {
                    return null;
                }
                // if data is not empty, return data
                else {
                    return rs.getString("data");
                }
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

    public JSONObject getPass(String uuid) {
        try (PreparedStatement s = conn.prepareStatement("SELECT * FROM data WHERE uuid = ?")) {
            s.setString(1, uuid);
            ResultSet rs = s.executeQuery();
            JSONObject json = new JSONObject();

            while (rs.next()) {
                json.put("name", rs.getString("name"));
                json.put("username", rs.getString("username"));
                json.put("password", rs.getString("password"));
                json.put("url", rs.getString("url"));
                json.put("notes", rs.getString("notes"));
            }
            System.out.println("json tostring" + json.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}