package com.hass.ali.doctorsapp;

/**
 * Created by Ali.Zohair on 8/23/2017.
 */

public class ScheduleBean {

    String scheduleName;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    String scheduleID;
    String scheduleTimeTo;
    String scheduleTimeFrom;
    String ScheduleTimeTo;
    String[] days;
    int capacity;


    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleTimeTo() {
        return scheduleTimeTo;
    }

    public void setScheduleTimeTo(String scheduleTimeTo) {
        this.scheduleTimeTo = scheduleTimeTo;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getScheduleTimeFrom() {
        return scheduleTimeFrom;
    }

    public void setScheduleTimeFrom(String scheduleTimeFrom) {
        this.scheduleTimeFrom = scheduleTimeFrom;
    }
}
