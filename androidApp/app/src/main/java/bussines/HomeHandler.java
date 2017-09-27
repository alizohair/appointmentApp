package bussines;

import android.database.Cursor;

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


        for (int a= 0 ; a < scheduleBeanList.size();a++){

            String daysSelect = "select day_id from schedule_day where schedule_id = ?";
            String[] whereClause = {scheduleBeanList.get(a).getScheduleID()};
            Cursor daysCursor = DBConnection.rawQuery(daysSelect, whereClause);
            ArrayList<String> days = new ArrayList<>();
            if (daysCursor.moveToFirst()) {
                do {
                    days.add(daysCursor.getString(0));


                } while (daysCursor.moveToNext());
            }
            scheduleBeanList.get(a).setDays(days);

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
                scheduleBeanList.add(scheduleBean);
            } while (cursor.moveToNext());
        }


        //   db.close();
        return scheduleBeanList;
    }
}
