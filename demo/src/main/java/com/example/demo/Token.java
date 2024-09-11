package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Token {
    private String token;
    private LocalDateTime date;
    private LocalDateTime time_expaire;

    public Token() {
        this.token = String.valueOf(UUID.randomUUID());
        this.date = LocalDateTime.now();
        this.time_expaire = this.date.plusMinutes(5);
    }

    public Token(String token) {
        this.token = token;
        this.date = LocalDateTime.now();
        this.time_expaire = this.date.plusMinutes(5);
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isActive() {
        return time_expaire.isAfter(LocalDateTime.now());
    }

    public void disableToken(){
        time_expaire = LocalDateTime.parse( "1992-09-02T00:00:00" );
    }
}
