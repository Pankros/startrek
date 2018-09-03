package ar.com.meli.startrek.rs.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestService {

    @RequestMapping("/")
    public String index() {
        return "Greetings from this planetaty system!";
    }

}