package com.example.betterus_tutorial.user.dataObjects;

public class ActivityInfo {
    private String activityName;
    private TimeInfo activityTime;
    private GoalInfo goalInfo;

    public ActivityInfo(String activityName, TimeInfo activityTime, GoalInfo goalInfo){
        this.activityName = activityName;
        this.activityTime = activityTime;
        this.goalInfo = goalInfo;
    }

    public ActivityInfo(){}

    public void setActivityName(String actName){
        this.activityName = actName;
    }

    public void setActivityTime(TimeInfo timeInfo){
        this.activityTime = timeInfo;
    }

    public void setGoalInfo(GoalInfo goalInfo){this.goalInfo = goalInfo;}

    public String getActivityName(){return this.activityName;}

    public TimeInfo getActivityTime(){return this.activityTime;}

    public GoalInfo getGoalInfo(){return this.goalInfo;}
}
