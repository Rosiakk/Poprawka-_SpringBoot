package com.example.demo;

import java.awt.*;

public class Pixel {
    public int x;
    public int y;
    public Color color;
    public String token;
    public Pixel(int x, int y, String color, String token) {
        this.x = x;
        this.y = y;
        this.color = Color.decode(color);
        this.token = token;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = Color.decode(color);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
