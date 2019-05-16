package com.example.dneprovdanila.litpro_project;

public class Composition {

    /*
        composition - само сочинение
        composition_title - заголовок сочинения
        checked - проверено ли сочинение
        words_count - число слов  в сочинении
        author_id - id ученика ( автора )
     */

    private  String author_id;
    private  String composition_title;
    private String composition;
    private  Boolean checked;
    private Integer words_count;
    private String author_name;



    private String feedback;
    private Integer mark;



    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getComposition_title() {
        return composition_title;
    }

    public void setComposition_title(String composition_title) {
        this.composition_title = composition_title;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getWords_count() {
        return words_count;
    }

    public void setWords_count(Integer words_count) {
        this.words_count = words_count;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }


    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }


    public Composition(){}

    public  Composition(String author_id,  String author_name, String composition_title, String composition, Boolean checked, String feedback, Integer mark, Integer words_count) {
        this.author_id = author_id;
        this.composition_title = composition_title;
        this.composition = composition;
        this.checked = checked;
        this.feedback = feedback;
        this.words_count = words_count;
        this.mark = mark;
        this.author_name = author_name;
    }
}
