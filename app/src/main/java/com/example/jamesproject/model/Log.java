package com.example.jamesproject.model;

public class Log {
static String[] cowData={
        "ID",
        "Weight",
        "Age",
        "Condition"

};
public  class cowLogs{
    private  int ID;
    private  int Weight;
    private  int Age;
    private  String Condition;

    public cowLogs(int ID, int Weight, int Age, String Condition){
        this.ID=ID;
        this.Weight=Weight;
        this.Age=Age;
        this.Condition=Condition;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }
}
}
