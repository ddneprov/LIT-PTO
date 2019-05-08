package com.example.dneprovdanila.litpro_project;

public class User {
    private String name;
    private String email;
    private String password;
    private String id;
    private Integer points;
    private String teacher_id;

    public User() { }

    public User(String email, String name, String password, Integer points, String id, String teacher_id) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.points = points;
        this.id = id;
        this.teacher_id = teacher_id;
    }


    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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