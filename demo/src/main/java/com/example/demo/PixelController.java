package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class PixelController {
    @PostMapping("/pixel")
    public ResponseEntity<String> addPixel(@RequestBody Pixel pixel){
        if(!AuthController.CheckToken(pixel.token)){
            return new ResponseEntity<>("Niewłaśiwy token", HttpStatus.FORBIDDEN);
        }

        if(!(pixel.x >= 0 && pixel.x  <= 512 && pixel.y >= 0 && pixel.y  <= 512)){
            return new ResponseEntity<>("Złe wartości X i Y", HttpStatus.BAD_REQUEST);
        }

        Image.drawPixel(ImageController.image.image, pixel);
        insertPixelData(pixel);

        return ResponseEntity.ok().build();
    }

    public static void rollbackImage(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:baza_danych.db");

            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM entry");

            while(rs.next()){
                String token = rs.getString("token");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                String color = rs.getString("color");

                System.out.println(color);

                Pixel pixel = new Pixel(x, y, color, token);
                Image.drawPixel(ImageController.image.image, pixel);
            }
        }catch(SQLException exception){
            System.err.println(exception.getMessage());
        }
    }

    @GetMapping("/create_table")
    public String createTable(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:baza_danych.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);");
        }catch(SQLException exception){
            System.err.println(exception.getMessage());
            return "wyebao";
        }

        return "tabela została stworzona";
    }

    public void insertPixelData(Pixel pixel){
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:baza_danych.db");
                PreparedStatement statement = conn.prepareStatement("INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?);");

                statement.setString(1, pixel.getToken());
                statement.setInt(2, pixel.getX());
                statement.setInt(3, pixel.getY());
                statement.setString(4, "#" + Integer.toHexString(
                        pixel.getColor().getRed()) +
                        Integer.toHexString(pixel.getColor().getGreen()) +
                        Integer.toHexString(pixel.getColor().getBlue()));
                statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                statement.execute();
            }catch(SQLException exception) {
                System.err.println(exception.getMessage());
            }
    }
}