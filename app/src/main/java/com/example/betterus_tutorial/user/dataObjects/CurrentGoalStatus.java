package com.example.betterus_tutorial.user.dataObjects;

// Used to store current user's goal status
public class CurrentGoalStatus {
    private int caloriesBurnt, caloriesGained, numMeditation, numExercise;

    public CurrentGoalStatus(int caloriesBurnt, int caloriesGained,
                             int numMeditation, int numExercise){
        this.caloriesBurnt = caloriesBurnt;
        this.caloriesGained = caloriesGained;
        this.numMeditation = numMeditation;
        this.numExercise = numExercise;
    }

    private void setCaloriesBurnt(int caloriesBurnt){ // GOOD
        this.caloriesBurnt = caloriesBurnt;
    }

    private void setCaloriesGained(int caloriesGained){ // GOOD
        this.caloriesGained = caloriesGained;
    }

    private void setNumMeditation(int numMeditation){ // GOOD
        this.numMeditation = numMeditation;
    }

    public void setNumExercise(int numExercise){ // GOOD
        this.numExercise = numExercise;
    }

    private int getCaloriesBurnt(){ // GOOD
        return this.caloriesBurnt;
    }

    private int getCaloriesGained(){ // GOOD
        return this.caloriesGained;
    }

    public int getNumMeditation(){ // GOOD
        return this.numMeditation;
    }

    public int getNumExercise(){ // GOOD
        return this.numExercise;
    }
}
