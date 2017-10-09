package bussines;

import android.database.Cursor;

import com.hass.ali.doctorsapp.PatientBean;
import com.hass.ali.doctorsapp.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

import dataBase.DBConnection;

/**
 * Created by ali.zohair on 9/16/2017.
 */

public class HomeHandler {




    public List<ScheduleBean> getscheduleList() throws Exception{

        // SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String selectQuery = "select schedule_id ID,schedule_name name,start_time startTime,end_time endtime,start_date startDate,end_date endDate,capacity capacity from schedule;";
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
                scheduleBean.setScheduledateFrom(cursor.getString(4));
                scheduleBean.setScheduledateto(cursor.getString(5));

                scheduleBean.setCapacity(cursor.getString(6));
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

}
