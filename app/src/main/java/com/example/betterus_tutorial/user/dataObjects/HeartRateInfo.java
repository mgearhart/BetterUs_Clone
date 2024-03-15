package com.example.betterus_tutorial.user.dataObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Has not been integrated within ActivityHolderLog, do not use
@Deprecated
public class HeartRateInfo {
    private final static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
    private int heartRate;
    private String dateTime;

    public HeartRateInfo(int heartRate, Date dateTime){ // GOOD
        this.heartRate = heartRate;
        this.dateTime = formatter.format(dateTime);
    }

    public HeartRateInfo(){} // GOOD

    public void setHeartRate(int heartRate){
        this.heartRate = heartRate;
    } // GOOD

    public void setDateTime(Date dateTime){
        this.dateTime = formatter.format(dateTime);
    } // GOOD

    public int getHeartRate(){return this.heartRate;} // GOOD

    public String getDateTime(){return this.dateTime;} // GOOD
}
