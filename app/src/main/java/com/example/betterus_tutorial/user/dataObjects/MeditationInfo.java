package com.example.betterus_tutorial.user.dataObjects;

public class MeditationInfo {
    public final static int NUM_ACTIVITIES = 3;
    private final ActivityInfo actInfoArray[];

    public MeditationInfo(int size){
        this.actInfoArray = new ActivityInfo[size];
    }

    public void setActivity(ActivityInfo actInfo, int pos){
        this.actInfoArray[pos] = actInfo;
    }

    public ActivityInfo getActivity(int pos){return this.actInfoArray[pos];}
}
