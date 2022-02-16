package server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser;

@RestController
@RequestMapping("/api")
public class APIRestController {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello World! from my Spring Boot REST API";
    }

    @GetMapping("/getUser/{username}")
    public JSONObject getUser(@PathVariable("id") String username){
        DBLink db = new DBLink();
        return DBLink.findUser(username);

    }
    


}
