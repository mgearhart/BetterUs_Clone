package com.example.betterus_tutorial.user.dataObjects;

import java.util.ArrayList;

public class UserLog {
    private ArrayList <DailyGoal> dailyGoalsLog;
    private ArrayList <MealInfo> mealLog;
    private ArrayList <ActivityInfoLog> meditationLog, exerciseLog;

    public UserLog(ArrayList <DailyGoal> dailyGoalsLog, ArrayList <MealInfo> mealLog,
                   ArrayList <ActivityInfoLog> meditationLog,
                   ArrayList <ActivityInfoLog> exerciseLog){ // GOOD
        this.dailyGoalsLog = dailyGoalsLog;
        this.mealLog = mealLog;
        this.meditationLog = meditationLog;
        this.exerciseLog = exerciseLog;
    }

    public UserLog(){}

    public void setDailyGoalsLog(ArrayList <DailyGoal> dailyGoalsLog){ // GOOD
        this.dailyGoalsLog = dailyGoalsLog;
    }

    public void setMealLog(ArrayList <MealInfo> mealLog){ // GOOD
        this.mealLog = mealLog;
    }

    public void setMeditationLog(ArrayList <ActivityInfoLog> meditationLog){ // GOOD
        this.meditationLog = meditationLog;
    }

    public void setExerciseLog(ArrayList <ActivityInfoLog> exerciseLog){ // GOOD
        this.exerciseLog = exerciseLog;
    }

    public ArrayList <DailyGoal> getDailyGoalsLog(){ // GOOD
        return this.dailyGoalsLog;
    }

    public ArrayList <MealInfo> getMealLog(){ // GOOD
        return this.mealLog;
    }

    public ArrayList <ActivityInfoLog> getMeditationLog(){ // GOOD
        return this.meditationLog;
    }

    public ArrayList <ActivityInfoLog> getExerciseLog(){ // GOOD
        return this.exerciseLog;
    }
}
