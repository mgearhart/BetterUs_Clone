package com.example.betterus_tutorial.user.dataObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealInfo {
    private final static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
    private String name;
    private String dateTime;
    private ArrayList<FoodInfo> foods;

    public MealInfo(String name, ArrayList<FoodInfo> foods, Date dateTime){ // GOOD
        this.name = name;
        this.foods = foods;
        this.dateTime = formatter.format(dateTime);
    }

    public MealInfo(){} // GOOD

    public void setName(String name){
        this.name = name;
    } // GOOD

    public void setFoods(ArrayList<FoodInfo> foods){
        this.foods = foods;
    } // GOOD

    public void setFood(FoodInfo food){
        this.foods.add(food);
    } // GOOD

    public void setDateTime(Date dateTime){
        this.dateTime = formatter.format(dateTime);
    } // GOOD

    public void setDateTme(String dateTimeStr){this.dateTime = dateTimeStr;} // GOOD

    public String getName(){return this.name;} // GOOD

    public ArrayList<FoodInfo> getFoods(){return this.foods;} // GOOD

    public FoodInfo getFood(int pos){return this.foods.get(pos);} // GOOD
}
