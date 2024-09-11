package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {
    private static List<Token> tokens = new ArrayList<>();

    @PostMapping ("/register")
    public Token register(){
        Token token = new Token();
        tokens.add(token);

        return token;
    }

    @GetMapping("/tokens")
    public List<Token> tokens(){
        return tokens;
    }

    public static boolean CheckToken(String tokenToCheck){
        for(Token token : tokens){
            if(token.getToken().equals(tokenToCheck) && token.isActive()){
                return true;
            }
        }

        return false;
    }

    public static void BanToken(String tokenToBan){
        for(Token token : tokens){
            if(token.getToken().equals(tokenToBan)){
                token.disableToken();
            }
        }
    }
}
