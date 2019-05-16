package com.example.dneprovdanila.litpro_project;

import java.util.ArrayList;

public class Staff {

    /*
    points - количество баллов проверяющего ( в зависимости от кол-ва проверенных работ )
    pupils - лист с учениками, которых взялся проверять проверяющий
     */
    private   String name;
    private String email;
    private Integer points;                                     // количество баллов
    private String status;                                      // статус проверяющего
    private String id;                                          // id проверяющего
    private ArrayList<String> pupils = new ArrayList<String>(); // список ,прикрепленных к проверяющему, студентов


    public void Add_New_Pupil(String pupil_id)
    {
        if (pupil_id!=null)
            getPupils().add(pupil_id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

   /* public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
*/
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

    public Staff() {

    }

    public Staff(String name, String email,/* String password, */ ArrayList<String> pupils, Integer points, String status, String id) {
        this.name = name;
        this.email = email;
        //this.password = password;
        this.pupils = pupils;
        this.points = points;
        this.status = status;
        this.id = id;
    }
}
