package bussines;

import android.content.ContentValues;
import android.database.Cursor;

import com.hass.ali.doctorsapp.DepartmentArrayAdapter;
import com.hass.ali.doctorsapp.PatientBean;
import com.hass.ali.doctorsapp.ScheduleBean;
import com.hass.ali.doctorsapp.appointmentBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dataBase.DBConnection;

/**
 * Created by ali.zohair on 9/16/2017.
 */

public class HomeHandler {




    public List<ScheduleBean> getscheduleList() throws Exception{

        // SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String selectQuery = "select a.dayid dayid,s.schedule_id ID,schedule_name name,start_time startTime,end_time endtime,start_date startDate,end_date endDate,capacity capacity from schedule s left join (select group_concat(day_id) dayid,schedule_id sid from schedule_day group by schedule_id) as a ON  s.schedule_id = a.sid";
        Cursor cursor =  DBConnection.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ScheduleBean> scheduleBeanList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setScheduleID(cursor.getString(cursor.getColumnIndex("ID")));
                scheduleBean.setScheduleName(cursor.getString(cursor.getColumnIndex("name")));


                scheduleBean.setScheduleTimeTo(cursor.getString(cursor.getColumnIndex("startTime")));

                scheduleBean.setScheduleTimeFrom(cursor.getString(cursor.getColumnIndex("endtime")));

                scheduleBean.setScheduledateFrom(cursor.getString(cursor.getColumnIndex("startDate")));

                scheduleBean.setScheduledateto(cursor.getString(cursor.getColumnIndex("endDate")));

                scheduleBean.setCapacity(cursor.getString(cursor.getColumnIndex("capacity")));



                String[] dayIDs = cursor.getString(cursor.getColumnIndex("dayid")).split(",");

                scheduleBean.setDays(new ArrayList( Arrays.asList( dayIDs) ));

                scheduleBeanList.add(scheduleBean);
            } while (cursor.moveToNext());
        }


        //   db.close();
        return scheduleBeanList;
    }





    public ArrayList<ScheduleBean> getDayscheduleList(String Str_date,String day) throws Exception{

        String asd =   "where appointment_date = '"+Str_date+" ";
        // SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String selectQuery = "SELECT s.*, a.appoint_count " +
                "FROM schedule s " +
                "inner join schedule_day  sd " +
                "on s.schedule_id = sd.schedule_id " +
                "left join (select schedule_id, count(*) appoint_count " +
                "from appointment " +
                "where appointment_date = '"+Str_date+"' " +
                "group by schedule_id" +
                ") a on s.schedule_id = a.schedule_id " +
               // "WHERE '"+Str_date+"' between s.end_date and s.start_date " +
                " WHERE sd.day_id = '"+day+"';";
        String[] strings = {Str_date,Str_date,day};
        Cursor cursor =  DBConnection.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ScheduleBean> scheduleBeanList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setScheduleID(cursor.getString(0));
                scheduleBean.setScheduleName(cursor.getString(1));
                scheduleBean.setScheduleTimeTo(cursor.getString(2));
                scheduleBean.setScheduleTimeFrom(cursor.getString(3));
                scheduleBean.setCapacity(cursor.getString(6));
                scheduleBean.setAppointmentCount(cursor.getString(7));
                scheduleBean.setCurrentDate(Str_date);
                scheduleBeanList.add(scheduleBean);
            } while (cursor.moveToNext());
        }


        //   db.close();
        return scheduleBeanList;
    }



    public ArrayList<appointmentBean> getAppointmentList(String scheduleId,String appointmentDate,String status) throws Exception{
        //
        String selectQuery = "select * from appointment a inner join patient p on a.client_id = p.patient_id where schedule_id = ? and appointment_date = ? and status = coalesce(?, status) order by a.token_no";
        String[] strings = {scheduleId,appointmentDate,status};
        Cursor cursor =  DBConnection.rawQuery(selectQuery, strings);
        // Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<appointmentBean> appointmentBeanList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {


                PatientBean patientBean = new PatientBean();
                patientBean.setPatientID(cursor.getString(cursor.getColumnIndex("patient_id")));
                patientBean.setPatientName(cursor.getString(cursor.getColumnIndex("patient_name")));
                patientBean.setContactNo(cursor.getString(cursor.getColumnIndex("contact_no")));
                appointmentBean appointmentBean = new appointmentBean();
                //appointmentBean.setAppointment_date(cursor.getString(cursor.getColumnIndex("schedule_id")));
                appointmentBean.setAppointment_id(cursor.getInt(cursor.getColumnIndex("appointment_id")));
                appointmentBean.setSchedule_id(cursor.getInt(cursor.getColumnIndex("schedule_id")));
                appointmentBean.setClient_id(cursor.getInt(cursor.getColumnIndex("client_id")));
                appointmentBean.setAppointment_date(cursor.getString(cursor.getColumnIndex("appointment_date")));
                appointmentBean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                appointmentBean.setToken_no(cursor.getString(cursor.getColumnIndex("token_no")));
                appointmentBean.setToken_datetime(cursor.getString(cursor.getColumnIndex("token_datetime")));
                appointmentBean.setToken_reorder_time(cursor.getString(cursor.getColumnIndex("token_reorder_time")));
                appointmentBean.setAvailed_time(cursor.getString(cursor.getColumnIndex("availed_time")));
                appointmentBean.setPatientBean(patientBean);
                appointmentBeanList.add(appointmentBean);
            } while (cursor.moveToNext());
        }
        return appointmentBeanList;
    }
     public ArrayList<ScheduleBean> getscheduleListFromDay(String dayId) throws Exception{
      //
        String selectQuery = "select * from schedule s  where exists (select * from schedule_day sd where sd.schedule_id = s.schedule_id and sd.day_id = ?);";
        String[] strings = {dayId};
        Cursor cursor =  DBConnection.rawQuery(selectQuery, strings);
        // Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ScheduleBean> scheduleBeanList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setScheduleID(cursor.getString(cursor.getColumnIndex("schedule_id")));
                scheduleBean.setScheduleName(cursor.getString(cursor.getColumnIndex("schedule_name")));
                scheduleBean.setScheduleTimeTo(cursor.getString(cursor.getColumnIndex("start_time")));
                scheduleBean.setScheduleTimeFrom(cursor.getString(cursor.getColumnIndex("end_time")));
                scheduleBean.setCapacity(cursor.getString(cursor.getColumnIndex("capacity")));

                scheduleBeanList.add(scheduleBean);
            } while (cursor.moveToNext());
        }
        return scheduleBeanList;
    }


    public ArrayList<PatientBean> getPatient() throws Exception{


        String select = "select patient_id,patient_name,contact_no from patient";
        Cursor cursor =  DBConnection.rawQuery(select , null);

        ArrayList<PatientBean> patientBeen = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                PatientBean patientBean = new PatientBean();
                patientBean.setPatientID(cursor.getString(0));
                patientBean.setPatientName(cursor.getString(1));
                patientBean.setContactNo(cursor.getString(2));

                patientBeen.add(patientBean );
            } while (cursor.moveToNext());
        }

            return patientBeen;


    }



    public HashMap<String, Boolean> getMonthCapacity(String dateFrom, String dateTo) throws Exception{


        String select = "SELECT appointment_date, SUM(NoOfAppointments) NoOfAppointments, SUM(Capacity) Capacity FROM ( SELECT appointment_date, COUNT(*) NoOfAppointments, MAX(capacity) Capacity FROM appointment a INNER JOIN schedule s on a.schedule_id = s.schedule_id WHERE appointment_date between ? and ? GROUP BY appointment_date, s.schedule_id ) GROUP BY appointment_date";

        String[] where = {dateFrom,dateTo};
        Cursor cursor =  DBConnection.rawQuery(select , where);
        HashMap<String,Boolean> stringDateCapacityHashMap = new HashMap<>();
        ArrayList<DateCapacity> patientBeen = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                boolean full = false;

                int appointment = cursor.getInt(cursor.getColumnIndex("NoOfAppointments"));
                int capacity = cursor.getInt(cursor.getColumnIndex("Capacity"));
                if(appointment == capacity)
                    full = true;


                stringDateCapacityHashMap.put(cursor.getString(cursor.getColumnIndex("appointment_date")),full);


            } while (cursor.moveToNext());
        }

            return stringDateCapacityHashMap;


    }


public void assignTokenNumber(String appointmentDate,String appointmentID,String scheduleID,String tokenNumber) throws Exception {


    ContentValues appointmentCv = new ContentValues();


    appointmentCv.put("token_no",tokenNumber);
    appointmentCv.put("token_reorder_time",tokenNumber);


    String[] where = {appointmentID,scheduleID,appointmentDate};

    DBConnection.updateRecord("appointment", appointmentCv,"appointment_id = ? and schedule_id = ? and appointment_date = ? ",where);



}
public boolean changeStatus(String appointmentDate,String appointmentID,String scheduleID,String Status,String tokenNo,String tokenDateTime,String availDateTime,String reorderTime) throws Exception {


    ContentValues appointmentCv = new ContentValues();

    appointmentCv.put("status",Status);

if(Status.equalsIgnoreCase("pending|deleted") ||  tokenNo != null){

    appointmentCv.put("token_no",tokenNo);
}

if(tokenDateTime != null){

        appointmentCv.put("token_datetime",tokenDateTime);
    }

    if(Status.equalsIgnoreCase("tokened") || availDateTime != null){

        appointmentCv.put("availed_time",availDateTime);
    }


    if(reorderTime != null){

        appointmentCv.put("token_reorder_time",reorderTime);
    }


String[] where = {appointmentID,scheduleID,appointmentDate};


    DBConnection.updateRecord("appointment", appointmentCv,"appointment_id = ? and schedule_id = ? and appointment_date = ? ",where);



    return true;


}

    public int getNewId(String tableName) throws Exception{

        //  SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        String count = "";
        String selectQuery = " select count (*) from " +tableName+";";
        //   db = databaseHandler.getWritableDatabase();

        Cursor cursor = DBConnection.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            count = cursor.getString(0);

        }
        //     db.close();

        return Integer.parseInt(count) + 1;


    }


   public int getNewToken() throws Exception{

        //  SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        String count = "";
        String selectQuery = "select  ifnull (max(TOKEN_NO),\"0\") as newToken from appointment;";
        //   db = databaseHandler.getWritableDatabase();

        Cursor cursor = DBConnection.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            count = cursor.getString(0);

        }
        //     db.close();

        return Integer.parseInt(count) + 1;


    }





   public int getAppoinmentCount(String scheduleID) throws Exception{

        //  SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        String count = "";
        String selectQuery = "select count(*) from appointment where schedule_id = ?;";
        //   db = databaseHandler.getWritableDatabase();
       String[] where ={scheduleID};

        Cursor cursor = DBConnection.rawQuery(selectQuery, where);

        if (cursor.moveToFirst()) {

            count = cursor.getString(0);

        }
        //     db.close();

        return Integer.parseInt(count);


    }


   public boolean deleteSchedule(String scheduleID) throws Exception{


                        int count =  getAppoinmentCount(scheduleID);

        boolean deletet = false;
       if(count<1){


           String[] where ={scheduleID};

           deletet=  DBConnection.deleteSKURecord("schedule","schedule_id = ?", where);
          if(deletet)
           deletet=  DBConnection.deleteSKURecord("schedule_day","schedule_id = ?", where);

         //  deletet = true;

       }



        return deletet;


    }


    public boolean deleteScheduleDay(String scheduleID) throws Exception{


                        int count =  getAppoinmentCount(scheduleID);

        boolean deletet = false;
       if(count<1){


           String[] where ={scheduleID};

           deletet=  DBConnection.deleteSKURecord("schedule_day","schedule_id = ?", where);

         //  deletet = true;

       }



        return deletet;


    }




















}
