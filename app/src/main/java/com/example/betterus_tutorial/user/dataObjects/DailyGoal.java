package com.example.betterus_tutorial.user.dataObjects;

public class DailyGoal extends GoalInfo{
    private String startTime;

    public DailyGoal(int caloriesBurnt, int caloriesGained, int numMeditation,
                     int numExercises, String startTime){ // GOOD
        super(caloriesBurnt, caloriesGained, numMeditation, numExercises);
        this.startTime = startTime;
    }

    public DailyGoal(){}

    public void setStartTime(String startTime){ // GOOD
        this.startTime = startTime;
    }

    public String getStartTime(){ // GOOD
        return this.startTime;
    }
}
