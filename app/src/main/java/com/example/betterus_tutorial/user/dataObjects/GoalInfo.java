package com.example.betterus_tutorial.user.dataObjects;

public class GoalInfo {
    private int currentDays, totalDays;

    public GoalInfo(int currentDays, int totalDays){
        this.currentDays = currentDays;
        this.totalDays = totalDays;
    }

    public GoalInfo(){}

    public void setCurrentDays(int days){
        this.currentDays = days;
    }

    public void setTotalDays(int days){
        this.totalDays = days;
    }

    public int getCurrentDay(){return this.currentDays;}

    public int getTotalDays(){return this.totalDays;}
}
