package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dataBase.DatabaseHandler;


public class ScheduleListScreen extends AppCompatActivity {
// tasdlkasldj
    private RecyclerView scheduleList;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ScheduleListScreen.this,ScheduleForm.class));
            /*    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        databaseHandler = new DatabaseHandler(this);

             scheduleList = (RecyclerView)findViewById(R.id.schedule_list);
        ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(getscheduleList(),ScheduleListScreen.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        scheduleList.setLayoutManager(mLayoutManager);
        scheduleList.setItemAnimator(new DefaultItemAnimator());
        scheduleList.setAdapter(scheduleListAdapter);

       // CalendarView calendarView = (CalendarView)findViewById(R.id.compactcalendar_view);







    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private List<ScheduleBean> getscheduleList(){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String selectQuery = "select schedule_id ID,schedule_name name,start_time startTime,end_time endtime,start_date startDate,end_date endDate,capacity capacity from schedule;";

        Cursor cursor = db.rawQuery(selectQuery, null);
       ArrayList<ScheduleBean> scheduleBeanList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setScheduleID(cursor.getString(0));
            scheduleBean.setScheduleName(cursor.getString(1));
            scheduleBean.setScheduleTimeTo(cursor.getString(2));
            scheduleBean.setScheduleTimeFrom(cursor.getString(3));
            scheduleBean.setCapacity(cursor.getString(4));
            scheduleBeanList.add(scheduleBean);
            } while (cursor.moveToNext());
        }


for (int a= 0 ; a < scheduleBeanList.size();a++){

    String daysSelect = "select day_id from schedule_day where schedule_id = ?";
    String[] whereClause = {scheduleBeanList.get(a).getScheduleID()};
    Cursor daysCursor = db.rawQuery(daysSelect, whereClause);
    ArrayList<String> days = new ArrayList<>();
    if (daysCursor.moveToFirst()) {
        do {
            days.add(daysCursor.getString(0));


        } while (daysCursor.moveToNext());
    }
    scheduleBeanList.get(a).setDays(days);

}

        db.close();
        return scheduleBeanList;
    }
}
