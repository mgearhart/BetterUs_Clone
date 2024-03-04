package com.example.betterus_tutorial.user.dataObjects;

public class SleepInfo {
    private TimeInfo wakeUpTime, sleepTime;

    public SleepInfo(TimeInfo wakeUpTime, TimeInfo sleepTime){ // GOOD
        this.wakeUpTime = wakeUpTime;
        this.sleepTime = sleepTime;
    }

    public SleepInfo(){} // GOOD

    public void setWakeUpTime(TimeInfo wakeUpTime){
        this.wakeUpTime = wakeUpTime;
    } // GOOD

    public void setSleepTime(TimeInfo sleepTime){
        this.sleepTime = sleepTime;
    } // GOOD

    public TimeInfo getWakeUpTime(){return this.wakeUpTime;} // GOOD

    public TimeInfo getSleepTime(){return this.sleepTime;} // GOOD
}
