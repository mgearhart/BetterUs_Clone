package com.example.betterus_tutorial.user.dataObjects;

/*
    Class not in use and probably will not be used at all
 */
public class GoalInfo {
    private int currentDays, totalDays;

    public GoalInfo(int currentDays, int totalDays){ // GOOD
        this.currentDays = currentDays;
        this.totalDays = totalDays;
    }

    public GoalInfo(){} // GOOD

    public void setCurrentDays(int days){
        this.currentDays = days;
    } // GOOD

    public void setTotalDays(int days){
        this.totalDays = days;
    } // GOOD

    public int getCurrentDay(){return this.currentDays;} // GOOD

    public int getTotalDays(){return this.totalDays;} // GOOD
}
