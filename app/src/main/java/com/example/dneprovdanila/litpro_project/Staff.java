package com.example.dneprovdanila.litpro_project;

import java.util.ArrayList;

public class Staff {

    public  String name, email, password;
    public ArrayList<String> people = new ArrayList<String>();

    public Staff() {

    }

    public Staff(String name, String email, String password,  ArrayList<String> people) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.people = people;
    }


}
