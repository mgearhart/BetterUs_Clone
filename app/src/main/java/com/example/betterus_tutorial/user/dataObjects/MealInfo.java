package com.example.betterus_tutorial.user.dataObjects;

import java.util.ArrayList;

public class MealInfo {
    private String name;
    private ArrayList<FoodInfo> foods;

    public MealInfo(String name, ArrayList<FoodInfo> foods){
        this.name = name;
        this.foods = foods;
    }

    public MealInfo(){}

    public void setName(String name){
        this.name = name;
    }

    public void setFoods(ArrayList<FoodInfo> foods){
        this.foods = foods;
    }

    public void setFood(FoodInfo food){
        this.foods.add(food);
    }

    public String getName(){return this.name;}

    public ArrayList<FoodInfo> getFoods(){return this.foods;}

    public FoodInfo getFood(int pos){return this.foods.get(pos);}
}
