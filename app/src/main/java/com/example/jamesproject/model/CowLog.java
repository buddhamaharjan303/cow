package com.example.jamesproject.model;

import java.util.Locale;

public class CowLog {
    private Integer id;
    private String timeEntry;
    private Float weight;
    private Integer age;
    private String condition;
    private int cowId;

    public CowLog(String id, String timeEntry,String weight, String age, String condition,int cowId) {
        this.id = Integer.valueOf(id);
        this.timeEntry = timeEntry;
        this.weight = Float.valueOf(weight);
        this.age = Integer.valueOf(age);
        this.condition = condition;
        this.cowId = cowId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getCowId() {
        return cowId;
    }

    public void setCowId(int cowId) {
        this.cowId = cowId;
    }

    public String getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(String timeEntry) {
        this.timeEntry = timeEntry;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"%d %s %f %d %s",
                this.id,
                this.timeEntry,
                this.weight,
                this.age,
                this.condition);
    }
}
