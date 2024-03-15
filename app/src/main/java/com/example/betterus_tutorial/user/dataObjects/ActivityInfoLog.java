package com.example.betterus_tutorial.user.dataObjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityInfoLog extends ActivityInfo{
    private String startTime;
    private int heartRate;

    public ActivityInfoLog(String name, int calPerHour, Date startTime, int heartRate){ // GOOD
        super(name, calPerHour);
        this.startTime = SimpleDateFormat.getDateInstance().format(startTime);
        this.heartRate = heartRate;
    }

    public ActivityInfoLog(){}

    public void setStartTime(Date startTime){ // GOOD
        this.startTime = SimpleDateFormat.getDateInstance().format(startTime);
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
