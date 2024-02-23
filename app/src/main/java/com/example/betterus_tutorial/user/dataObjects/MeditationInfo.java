package com.example.betterus_tutorial.user.dataObjects;

import java.util.ArrayList;
import java.util.HashMap;

public class MeditationInfo {
    public final static int NUM_ACTIVITIES = 3;
    private final HashMap<String, ActivityInfo> activities;

    public MeditationInfo(){
        this.activities = new HashMap<>();
    }

    public void setActivity(String name, ActivityInfo actInfo){
        this.activities.put(name, actInfo);
    }

    public HashMap<String, ActivityInfo> getActivities(){
        return this.activities;
    }

    public ActivityInfo getActivity(String name){return this.activities.get(name);}
}
