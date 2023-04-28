package com.example.circuitbreakerclient;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("client")
public class Controller {
    @PostMapping("addUser")
    public HttpStatus addUser(@RequestParam("name") String name,@RequestParam("surname") String surname,
                          @RequestParam("age") Integer age){
        ApiClient.sendMessageServer(name, surname, age);
        return HttpStatus.OK;
    }
}
