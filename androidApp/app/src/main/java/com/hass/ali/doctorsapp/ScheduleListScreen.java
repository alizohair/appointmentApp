package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
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

        getscheduleList();












    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private List<ScheduleBean> getscheduleList(){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String selectQuery = "select schedule_name name,start_time startTime,end_time endtime,start_date startDate,end_date endDate,capacity capacity from schedule;";

        Cursor cursor = db.rawQuery(selectQuery, null);
       ArrayList<ScheduleBean> scheduleBeen = new ArrayList<>();
        if (cursor.moveToFirst()) {




        }

        db.close();


        return null;
    }
}
