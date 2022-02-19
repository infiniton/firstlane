package server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;
import org.json.JSONArray;

@RestController
@RequestMapping("/api")
public class APIRestController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World! from my Spring Boot REST API";
    }

    @GetMapping("/getUser")
    public String getUser(@RequestParam String username) {
        DBLink db = new DBLink();
        return db.getLoginInfo(username).toString();
    }

    @GetMapping("/exists")
    public String exists(@RequestParam String username) {
        DBLink db = new DBLink();
        return db.findUser(username) == 0 ? "true" : "false";
    }

}
