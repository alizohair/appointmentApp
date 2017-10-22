package com.hass.ali.doctorsapp;

/**
 * Created by Hp on 10/22/2017.
 */

public class appointmentBean {

    public PatientBean getPatientBean() {
        return patientBean;
    }

    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }

    PatientBean patientBean;
    int appointment_id;
    int schedule_id;
    int client_id;
    String appointment_date;
    String status;
    String token_no;
    String token_datetime;
    String token_reorder_time;
    String availed_time;


    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken_no() {
        return token_no;
    }

    public void setToken_no(String token_no) {
        this.token_no = token_no;
    }

    public String getToken_datetime() {
        return token_datetime;
    }

    public void setToken_datetime(String token_datetime) {
        this.token_datetime = token_datetime;
    }

    public String getToken_reorder_time() {
        return token_reorder_time;
    }

    public void setToken_reorder_time(String token_reorder_time) {
        this.token_reorder_time = token_reorder_time;
    }

    public String getAvailed_time() {
        return availed_time;
    }

    public void setAvailed_time(String availed_time) {
        this.availed_time = availed_time;
    }




}
