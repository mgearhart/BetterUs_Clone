package com.example.betterus_tutorial.user.dataObjects;

public class GoalInfo {
    private int caloriesBurnt, caloriesGained, numMeditation, numExercise;

    public GoalInfo(int caloriesBurnt, int caloriesGained, int numMed, int numExer){ // GOOD
        this.caloriesBurnt = caloriesBurnt;
        this.caloriesGained = caloriesGained;
        this.numMeditation = numMed;
        this.numExercise = numExer;
    }

    public GoalInfo(){} // GOOD

    public void setCaloriesBurnt(int caloriesBurnt){
        this.caloriesBurnt = caloriesBurnt;
    } // GOOD

    public void setCaloriesGained(int caloriesGained){this.caloriesGained = caloriesGained;} // GOOD

    public void setNumMeditation(int numMed){this.numMeditation = numMed;} // GOOD

    public void setNumExercises(int numExer){this.numExercise = numExer;} // GOOD

    public int getCaloriesBurnt(){return this.caloriesBurnt;} // GOOD

    public int getCaloriesGained(){return this.caloriesGained;} // GOOD

    public int getNumMeditation(){return this.numMeditation;} // GOOD

    public int getNumExercise(){return this.numExercise;} // GOOD
}
