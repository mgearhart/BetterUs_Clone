package com.example.betterus_tutorial.user.dataObjects;

import java.util.HashMap;

public class ActivityHolder {
    public static final int NUM_ACTIVITIES = 3;

    private final HashMap<String, ActivityInfo> activities;

    public ActivityHolder(){
        this.activities = new HashMap<>();

        // Setting up activities
        for(int i = 0; i < NUM_ACTIVITIES; i++){
            ActivityInfo activity = new ActivityInfo();
            TimeInfo timeInfo = new TimeInfo(TimeInfo.AmPm.AM, -1);
            GoalInfo goalInfo = new GoalInfo(-1, -1);

            activity.setCalPerHour(-1);
            activity.setActivityName("");
            activity.setActivityTime(timeInfo);
            activity.setGoalInfo(goalInfo);
            activities.put("activity" + (i+1), activity);
        }
    }

    public void setActivity(String name, ActivityInfo actInfo){
        this.activities.put(name, actInfo);
    }

    public HashMap<String, ActivityInfo> getActivities(){
        return this.activities;
    }

    public ActivityInfo getActivity(String name){return this.activities.get(name);}

    // Just checks if the activities have been filled in or so
    public boolean activitiesFilled(){
        for(int i = 0; i < NUM_ACTIVITIES; i++){
            if(this.activities.get("activity" + (i+1)).getActivityName().equals("")) return false;
        }
        return true;
    }
}
