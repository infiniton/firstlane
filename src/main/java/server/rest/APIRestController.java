package server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.json.JSONObject;
import org.json.JSONArray;

@RestController
@RequestMapping("/api")
public class APIRestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    // @GetMapping("/exists")
    // public String exists(@RequestParam String username) {
    // DBLink db = new DBLink();
    // return db.findUser(username) == 0 ? "true" : "false";
    // }

    @GetMapping("/test")
    public String test(@RequestParam String username) {
        return username;
    }

    // post request to get user
    @GetMapping(value = "/user", produces = "application/json")
    public String getUser(@RequestParam String user) {
        System.out.println("GET Called with username: " + user);
        DBLink db = new DBLink();
        if (db.findUser(user) != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        JSONObject json = new JSONObject();
        json.put("user", user);
        json.put("password", db.getPassword(user));
        System.out.println(db.getData(user));
        json.put("data", db.getData(user));
        json.put("salt", db.getSalt(user));
        System.out.println(json.toString());
        return json.toString();
    }

    // post request to add user
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public String addUser(@RequestBody String body) {
        System.out.println("POST Called with body: " + body);
        DBLink db = new DBLink();
        JSONObject json = new JSONObject(body);
        String password = json.getString("password");
        String user = json.getString("user");
        String salt = json.getString("salt");
        String data = json.getString("data");

        if (db.findUser(user) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already taken");
        }
        if (db.addUser(user, password, salt, data) != 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error adding user");
        }
        return json.toString();
    }

    @PostMapping(value = "/password", consumes = "application/json", produces = "application/json")
    public String addPassword(@RequestBody String body) {
        System.out.println("POST Called with body: " + body);
        DBLink db = new DBLink();
        JSONObject json = new JSONObject(body);
        String user = json.getString("user");
        String name = json.getString("name");
        String username = json.getString("username");
        String password = json.getString("password");
        String url = json.getString("url");
        String notes = json.getString("notes");

        if (db.findUser(username) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username not found");
        }
        if (db.addPassword(user, name, username, password, url, notes) != 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error adding password");
        }
        return json.toString();
    }

}
