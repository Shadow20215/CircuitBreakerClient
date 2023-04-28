package com.example.circuitbreakerclient;

import java.time.LocalTime;
import java.util.Timer;

enum Status{
    OPEN,
    CLOSED
}
public class CircuitBreaker {
    private Status status;
    private LocalTime timer;

    public CircuitBreaker(Status status) {
        this.status = status;
    }
    public void startTimer(){
        timer = LocalTime.now();
    }

    /**
     * Время на востановление сервера - 15 секунд
     */
    public boolean timeIsOut(){
        return LocalTime.now().getSecond() - timer.getSecond() >= 15;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
