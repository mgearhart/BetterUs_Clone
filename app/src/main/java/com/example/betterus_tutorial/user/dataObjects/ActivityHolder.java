package com.example.betterus_tutorial.user.dataObjects;

import java.util.HashMap;

public class ActivityHolder {
    public static final int NUM_ACTIVITIES = 3;

    private final HashMap<String, ActivityInfo> activities;

    public ActivityHolder(){ // GOOD
        this.activities = new HashMap<>();

        // Setting up activities
        for(int i = 0; i < NUM_ACTIVITIES; i++){
            ActivityInfo activity = new ActivityInfo("", -1);
            activities.put("activity" + (i+1), activity);
        }
    }

    public void setActivity(String name, ActivityInfo actInfo){ // GOOD
        this.activities.put(name, actInfo);
    }

    public HashMap<String, ActivityInfo> getActivities(){
        return this.activities;
    } // GOOD

    public ActivityInfo getActivity(int actNum){return this.activities.get("activity" + actNum);} // GOOD

    // Just checks if the activities have been filled in or so
    public boolean activitiesFilled(){ // GOOD
        for(int i = 0; i < NUM_ACTIVITIES; i++){
            if(this.activities.get("activity" + (i+1)).getActivityName().equals("")) return false;
        }
        return true;
    }
}
