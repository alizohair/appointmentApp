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

import java.util.List;

import bussines.HomeHandler;
import bussines.RecyclerItemClickListener;


public class ScheduleListScreen extends AppCompatActivity {

    private RecyclerView scheduleList;
    private  List<ScheduleBean> scheduleBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        scheduleList = (RecyclerView)findViewById(R.id.schedule_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ScheduleListScreen.this,ScheduleForm.class));

            }
        });


        scheduleList.addOnItemTouchListener(new RecyclerItemClickListener(ScheduleListScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ScheduleBean scheduleBean =  scheduleBeanList.get(position);
                Intent intent = new Intent(ScheduleListScreen.this,ScheduleForm.class);
                intent.putExtra("ScheduleBean", scheduleBean);
                startActivity(intent);
            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeHandler homeHandler = new HomeHandler();

        ScheduleListAdapter scheduleListAdapter = null;
        try {

            scheduleBeanList = homeHandler.getscheduleList();
            scheduleListAdapter = new ScheduleListAdapter(scheduleBeanList,ScheduleListScreen.this);
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
