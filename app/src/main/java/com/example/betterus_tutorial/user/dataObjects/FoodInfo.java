package com.example.betterus_tutorial.user.dataObjects;

public class FoodInfo {
    private String name;
    private int calories;
    private float protein, fat, carbohydrates, fiber;

    public FoodInfo(String name, int calories, float protein, float fat, float carbohydrates, float fiber){ // GOOD
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
    }

    public FoodInfo(){} // GOOD

    public void setName(String name){
        this.name = name;
    } // GOOD

    public void setCalories(int cal){
        this.calories = cal;
    } // GOOD

    public void setProtein(float pro){
        this.protein = pro;
    } // GOOD

    public void setFat(float fat){
        this.fat = fat;
    } // GOOD

    public void setCarbohydrates(float car){
        this.carbohydrates = car;
    } // GOOD

    public void setFiber(float fiber){
        this.fiber = fiber;
    } // GOOD

    public String getName(){return this.name;} // GOOD

    public int getCalories(){return this.calories;} // GOOD

    public float getProtein(){return this.protein;} // GOOD

    public float getFat(){return this.fat;} // GOOD

    public float getCarbohydrates(){return this.carbohydrates;} // GOOD

    public float getFiber(){return this.fiber;} // GOOD
}
