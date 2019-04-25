package com.example.dneprovdanila.litpro_project;

public class User {
    private String name, email, password;
    private Integer points;


    public User() { }

    public User(String email, String name, String password, Integer points) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.points = points;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}