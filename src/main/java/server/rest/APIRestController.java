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

//    @GetMapping("/exists")
//    public String exists(@RequestParam String username) {
//        DBLink db = new DBLink();
 //       return db.findUser(username) == 0 ? "true" : "false";
 //   }

    // post request to get user
    @GetMapping(value = "/user", produces = "application/json")
    public String getUser(@RequestParam String username) {
        System.out.println("GET Called with username: " + username);
        DBLink db = new DBLink();
        if (db.findUser(username) != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", db.getPassword(username));
        System.out.println(db.getData(username));
        json.put("data", db.getData(username));
        json.put("salt", db.getSalt(username));
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
        String username = json.getString("username");
        String salt = json.getString("salt");
        String data = json.getString("data");

        if (db.findUser(username) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already taken");
        }
        if (db.addUser(username, password, salt, data) != 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error adding user");
        }
        return json.toString();
    }
}