package com.example.demo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;

public class Database {

    Connection conn;
    public Database(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza_danych.db");
        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }
        this.conn = null;
    }

    public void createTable(){
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);");
        }catch(SQLException exception){
            System.err.println(exception.getMessage());
        }
    }

    public void insertPixelData(Pixel pixel){
        if(conn != null){
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?);");

                statement.setString(1, pixel.getToken());
                statement.setInt(2, pixel.getX());
                statement.setInt(3, pixel.getY());
                statement.setString(4, "#"+Integer.toHexString(pixel.getColor().getRGB()));
                statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                statement.execute();
            }catch(SQLException exception){
                System.err.println(exception.getMessage());
            }
        }
    }
}