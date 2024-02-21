package com.example.betterus_tutorial.user.dataObjects;

public class ActivityInfo {
    private String activityName;
    private TimeInfo activityTime;

    public ActivityInfo(String activityName, TimeInfo activityTime){
        this.activityName = activityName;
        this.activityTime = activityTime;
    }

    public ActivityInfo(){}

    public void setActivityName(String actName){
        this.activityName = actName;
    }

    public void setActivityTime(TimeInfo timeInfo){
        this.activityTime = timeInfo;
    }

    public String getActivityName(){return this.activityName;}

    public TimeInfo getActivityTime(){return this.activityTime;}
}
