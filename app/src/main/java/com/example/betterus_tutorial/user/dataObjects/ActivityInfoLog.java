package com.example.betterus_tutorial.user.dataObjects;

public class ActivityInfoLog extends ActivityInfo{
    private String startTime;
    private int heartRate;

    public ActivityInfoLog(String name, int calPerHour, String startTime, int heartRate){ // GOOD
        super(name, calPerHour);
        this.startTime = startTime;
        this.heartRate = heartRate;
    }

    public ActivityInfoLog(){}
    public void setStartTime(String startTime){ // GOOD
        this.startTime = startTime;
    }

    public void setHeartRate(int heartRate){ // GODO
        this.heartRate = heartRate;
    }

    public String getStartTime(){ // GOOD
        return this.startTime;
    }

    public int getHeartRate(){ // GOOD
        return this.heartRate;
    }
}
