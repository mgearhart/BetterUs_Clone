package user.dataObjects;

public class SleepInfo {
    private TimeInfo wakeUpTime, sleepTime;

    public SleepInfo(TimeInfo wakeUpTime, TimeInfo sleepTime){
        this.wakeUpTime = wakeUpTime;
        this.sleepTime = sleepTime;
    }

    public SleepInfo(){}

    public void setWakeUpTime(TimeInfo wakeUpTime){
        this.wakeUpTime = wakeUpTime;
    }

    public void setSleepTime(TimeInfo sleepTime){
        this.sleepTime = sleepTime;
    }

    public TimeInfo getWakeUpTime(){return this.wakeUpTime;}

    public TimeInfo getSleepTime(){return this.sleepTime;}
}
