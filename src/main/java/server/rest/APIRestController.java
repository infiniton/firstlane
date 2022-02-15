package server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIRestController {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello World! from my Spring Boot REST API";
    }
}
