package com.example.betterus_tutorial.user.dataObjects;

public class HealthInfo {
    public enum BioSex{NONE, FEMALE, MALE};
    private float height, weight;
    private int age;
    private BioSex sex;

    public HealthInfo(BioSex sex, float height, float weight, int age){ // GOOD
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public HealthInfo(){} // GOOD

    public void setSex(BioSex sex){
        this.sex = sex;
    } // GOOD

    public void setHeight(float height){
        this.height = height;
    } // GOOD

    public void setWeight(float weight){
        this.weight = weight;
    } // GOOD

    public void setAge(int age){
        this.age = age;
    } // GOOD

    public BioSex getSex(){return this.sex;} // GOOD

    public float getHeight(){return this.height;} // GOOD

    public float getWeight(){return this.weight;} // GOOD

    public int getAge(){return this.age;} // GOOD
}
