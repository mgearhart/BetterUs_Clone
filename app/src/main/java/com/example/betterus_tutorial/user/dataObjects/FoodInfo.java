package com.example.betterus_tutorial.user.dataObjects;

public class FoodInfo {
    private String name;
    private int calories;
    private float protein, fat, carbohydrates, fiber;

    public FoodInfo(String name, int calories, float protein, float fat, float carbohydrates, float fiber){
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
    }

    public FoodInfo(){}

    public void setName(String name){
        this.name = name;
    }

    public void setCalories(int cal){
        this.calories = cal;
    }

    public void setProtein(float pro){
        this.protein = pro;
    }

    public void setFat(float fat){
        this.fat = fat;
    }

    public void setCarbohydrates(float car){
        this.carbohydrates = car;
    }

    public void setFiber(float fiber){
        this.fiber = fiber;
    }

    public String getName(){return this.name;}

    public int getCalories(){return this.calories;}

    public float getProtein(){return this.protein;}

    public float getFat(){return this.fat;}

    public float getCarbohydrates(){return this.carbohydrates;}

    public float getFiber(){return this.fiber;}
}
