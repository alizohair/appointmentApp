package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import bussines.HomeHandler;



public class ScheduleListScreen extends AppCompatActivity {

    private RecyclerView scheduleList;

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

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeHandler homeHandler = new HomeHandler();
        scheduleList = (RecyclerView)findViewById(R.id.schedule_list);
        ScheduleListAdapter scheduleListAdapter = null;
        try {
            scheduleListAdapter = new ScheduleListAdapter(homeHandler.getscheduleList(),ScheduleListScreen.this);
        } catch (Exception e) {
            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        scheduleList.setLayoutManager(mLayoutManager);
        scheduleList.setItemAnimator(new DefaultItemAnimator());
        scheduleList.setAdapter(scheduleListAdapter);



    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
