package com.hass.ali.doctorsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
    ScheduleListAdapter scheduleListAdapter = null;
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

        scheduleList.addOnItemTouchListener(new RecyclerItemClickListener(this,
                scheduleList, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well

                ScheduleBean scheduleBean =  scheduleBeanList.get(position);
                Intent intent = new Intent(ScheduleListScreen.this,ScheduleForm.class);
                intent.putExtra("ScheduleBean", scheduleBean);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                final ScheduleBean scheduleBean =  scheduleBeanList.get(position);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ScheduleListScreen.this);
                builder1.setMessage("Are you sure you want to delete.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                HomeHandler homeHandler = new HomeHandler();
                                try {

                               if(homeHandler.deleteSchedule(scheduleBean.scheduleID))
                               {

                                   scheduleBeanList = homeHandler.getscheduleList();
                                   scheduleListAdapter = new ScheduleListAdapter(scheduleBeanList,ScheduleListScreen.this);
                                   scheduleList.setAdapter(scheduleListAdapter);
                                   Toast.makeText(ScheduleListScreen.this, "Schedule deleted",
                                           Toast.LENGTH_LONG).show();


                               }else {
                                   Toast.makeText(ScheduleListScreen.this, "could not delete schedule, already appointment taken",
                                           Toast.LENGTH_LONG).show();
                               }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


              /*  Toast.makeText(ScheduleListScreen.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();*/
            }
        }));
       /* scheduleList.addOnItemTouchListener(new RecyclerItemClickListener(ScheduleListScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeHandler homeHandler = new HomeHandler();


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
