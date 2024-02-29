package com.example.betterus_tutorial.user.dataObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartRateInfo {
    public static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
    private int heartRate;
    private String dateTime;

    public HeartRateInfo(int heartRate, Date dateTime){
        this.heartRate = heartRate;
        this.dateTime = formatter.format(dateTime);
    }

    public HeartRateInfo(){}

    public void setHeartRate(int heartRate){
        this.heartRate = heartRate;
    }

    public void setDateTime(Date dateTime){
        this.dateTime = formatter.format(dateTime);
    }

    public int getHeartRate(){return this.heartRate;}

    public String getDateTime(){return this.dateTime;}
}
