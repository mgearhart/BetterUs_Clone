package com.example.betterus_tutorial.user.dataObjects;

public class TimeInfo {
    public enum AmPm {NONE, AM, PM};
    private AmPm amPm;
    private int time;

    public TimeInfo(AmPm amPm, int time){ // GOOD
        this.amPm = amPm;
        this.time = time;
    }

    public TimeInfo(){} // GOOD

    public void setAmPm(AmPm amPm){
        this.amPm = amPm;
    } // GOOD

    public void setTime(int time){
        this.time = time;
    } // GOOD

    public AmPm getAmPm(){return this.amPm;} // GOOD

    public int getTime(){return this.time;} // GOOD
}
