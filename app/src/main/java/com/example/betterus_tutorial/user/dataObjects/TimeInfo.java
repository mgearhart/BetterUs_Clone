package com.example.betterus_tutorial.user.dataObjects;

public class TimeInfo {
    public enum AmPm {NONE, AM, PM};
    private AmPm amPm;
    private int time;

    public TimeInfo(AmPm amPm, int time){
        this.amPm = amPm;
        this.time = time;
    }

    public TimeInfo(){}

    public void setAmPm(AmPm amPm){
        this.amPm = amPm;
    }

    public void setTime(int time){
        this.time = time;
    }

    public AmPm getAmPm(){return this.amPm;}

    public int getTime(){return this.time;}
}
