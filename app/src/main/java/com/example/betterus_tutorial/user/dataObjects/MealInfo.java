package com.example.betterus_tutorial.user.dataObjects;

import java.util.ArrayList;

public class MealInfo {
    private String name;
    private ArrayList<String> food;
    private String dateTime;

    public MealInfo(String name, ArrayList<String> foods, String dateTime){ // GOOD
        this.name = name;
        this.food = foods;
        this.dateTime = dateTime;
    }

    public MealInfo(){} // GOOD

    public MealInfo(MealInfo other){ // GOOD
        this.name = other.getName();
        this.food = other.getFoods();
        this.dateTime = other.getDateTime();
    }

    public void setName(String name){
        this.name = name;
    } // GOOD

    public void setFoods(ArrayList<String> foods){
        this.food = foods;
    } // GOOD

    public void setDateTime(String dateTimeStr){this.dateTime = dateTimeStr;} // GOOD

    public String getName(){return this.name;} // GOOD

    public ArrayList<String> getFoods(){return this.food;} // GOOD

    public String getDateTime(){return this.dateTime;} // GOOD
}
