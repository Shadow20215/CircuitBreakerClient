package com.example.circuitbreakerclient;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

public class ApiClient {
    private static final String baseURL = System.getenv("myUrl");
    private static final CircuitBreaker breaker = new CircuitBreaker(Status.CLOSED);

    public static void sendMessageServer(String name, String surname, Integer age){
        if(breaker.getStatus() == Status.CLOSED){
            try{
                restTemplate(createURL(name, surname, age));
            }catch (Exception ex){
                breaker.setStatus(Status.OPEN);
                breaker.startTimer();
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        else if(breaker.timeIsOut()){
            try {
                restTemplate(createURL(name, surname, age));
                breaker.setStatus(Status.CLOSED);
            }catch (Exception ex){
                breaker.startTimer();
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        else if (breaker.getStatus() == Status.OPEN && !breaker.timeIsOut())
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static String createURL(String name, String surname, Integer age){
        return baseURL +
                "?name=" + name +
                "&surname=" + surname +
                "&age=" + age;
    }
    private static void restTemplate(String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        new RestTemplate().exchange(url,
                HttpMethod.GET,
                entity,
                HttpStatus.class);
    }
}
