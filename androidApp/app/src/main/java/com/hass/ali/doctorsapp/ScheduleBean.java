package com.hass.ali.doctorsapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali.Zohair on 8/23/2017.
 */

public class ScheduleBean implements Serializable {
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    String currentDate;
    String scheduleName;
    String scheduleID;
    String scheduleTimeTo;
    String scheduleTimeFrom;

    public String getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(String appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    String appointmentCount;

    public String getScheduledateFrom() {
        return scheduledateFrom;
    }

    public void setScheduledateFrom(String scheduledateFrom) {
        this.scheduledateFrom = scheduledateFrom;
    }

    public String getScheduledateto() {
        return scheduledateto;
    }

    public void setScheduledateto(String scheduledateto) {
        this.scheduledateto = scheduledateto;
    }

    String scheduledateFrom;
    String scheduledateto;

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    ArrayList<String> days;
    String capacity;
    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }




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



    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getScheduleTimeFrom() {
        return scheduleTimeFrom;
    }

    public void setScheduleTimeFrom(String scheduleTimeFrom) {
        this.scheduleTimeFrom = scheduleTimeFrom;
    }
}
