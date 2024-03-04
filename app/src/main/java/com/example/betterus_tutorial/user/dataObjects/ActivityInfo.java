package com.example.betterus_tutorial.user.dataObjects;

public class ActivityInfo {
    private String activityName;
    private int caloriesPerHour;

    public ActivityInfo(String activityName, int calPerHour){ // GOOD
        this.activityName = activityName;
        this.caloriesPerHour = calPerHour;
    }

    public ActivityInfo(){} // GOOD

    public void setActivityName(String actName){
        this.activityName = actName;
    } // GOOD

    public void setCalPerHour(int calPerHour){
        this.caloriesPerHour = calPerHour;
    } // GOOD

    public String getActivityName(){return this.activityName;} // GOOD

    public int getCalPerHour(){return this.caloriesPerHour;} // GOOD
}
