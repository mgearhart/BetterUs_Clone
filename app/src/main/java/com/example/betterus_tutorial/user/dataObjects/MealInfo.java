package com.example.betterus_tutorial.user.dataObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealInfo {
    private String name;
    private ArrayList<String> food;
    private String dateTime;

    public MealInfo(String name, ArrayList<String> foods, Date dateTime){ // GOOD
        this.name = name;
        this.food = foods;
        this.dateTime = SimpleDateFormat.getDateTimeInstance().format(dateTime);
    }

    public MealInfo(){} // GOOD

    public void setName(String name){
        this.name = name;
    } // GOOD

    public void setFoods(ArrayList<String> foods){
        this.food = foods;
    } // GOOD

    public void setDateTime(Date dateTime){ // GOOD
        this.dateTime = SimpleDateFormat.getDateTimeInstance().format(dateTime);
    }

    public void setDateTme(String dateTimeStr){this.dateTime = dateTimeStr;} // GOOD

    public String getName(){return this.name;} // GOOD

    public ArrayList<String> getFoods(){return this.food;} // GOOD

    public String getDateTime(){return this.dateTime;} // GOOD
}
