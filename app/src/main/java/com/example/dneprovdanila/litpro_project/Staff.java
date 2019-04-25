package com.example.dneprovdanila.litpro_project;

import java.util.ArrayList;

public class Staff {

    /*
    points - количество баллов проверяющего ( в зависимости от кол-ва проверенных работ )
    pupils - лист с учениками, которых взялся проверять проверяющий
     */
    public  String name, email, password;
    public Integer points;


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

    public ArrayList<String> getPupils() {
        return pupils;
    }

    public void setPupils(ArrayList<String> pupils) {
        this.pupils = pupils;
    }

    public ArrayList<String> pupils = new ArrayList<String>();

    public Staff() {

    }

    public Staff(String name, String email, String password,  ArrayList<String> pupils, Integer points) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pupils = pupils;
        this.points = points;
    }


}
