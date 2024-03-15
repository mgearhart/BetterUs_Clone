package com.example.betterus_tutorial.user.dataObjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyGoal extends GoalInfo{
    private String startTime;

    public DailyGoal(int caloriesBurnt, int caloriesGained, int numMeditation,
                     int numExercises, Date startTime){ // GOOD
        super(caloriesBurnt, caloriesGained, numMeditation, numExercises);
        this.startTime = SimpleDateFormat.getDateInstance().format(startTime);
    }

    public DailyGoal(){}

    public void setStartTime(Date startTime){ // GOOD
        this.startTime = SimpleDateFormat.getDateInstance().format(startTime);
    }

    public String getStartTime(){ // GOOD
        return this.startTime;
    }
}
