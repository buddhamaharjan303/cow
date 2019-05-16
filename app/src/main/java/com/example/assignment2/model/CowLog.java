package com.example.assignment2.model;

import java.util.Locale;

public class CowLog {


    private Integer id;
    private String timeEntry;
    private Float weight;
    private Integer age;
    private String condition;
    private String cowName;
    private String latitude;
    private String longitude;

    public CowLog(String id, String timeEntry,String weight, String age, String condition,String cowName,String latitude,String longitude) {
        this.id             = Integer.valueOf(id);
        this.timeEntry      = timeEntry;
        this.weight         = Float.valueOf(weight);
        this.age            = Integer.valueOf(age);
        this.condition      = condition;
        this.cowName        = cowName;
        this.latitude = latitude;
        this.longitude      = longitude;
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

    public String getCowName() {
        return cowName;
    }

    public void setCowName(String cowName) {
        this.cowName = cowName;
    }

    public String getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(String timeEntry) {
        this.timeEntry = timeEntry;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"%s %s %s %s %d %s %s",
                this.condition,
                this.timeEntry,
                this.latitude,
                this.longitude,
                this.id,
                this.weight,
                this.age);
    }


    public String toEmailString() {
        return String.format(Locale.ENGLISH,"%s %s %s %s %s %d %s %s",
                this.cowName,
                this.condition,
                this.timeEntry,
                this.latitude,
                this.longitude,
                this.id,
                this.weight,
                this.age);
    }


}
