package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapters.CalendarCustomView;
import bussines.HomeHandler;
import bussines.RecyclerItemClickListener;


public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CalendarCustomView.OnLoadingButtonClickListener {
    private Calendar currentDate= Calendar.getInstance(Locale.ENGLISH);;
    private RecyclerView scheduleList;
     private ArrayList<ScheduleBean> scheduleBeen ;
    CalendarCustomView mView;
    TextView todayText;
    ScheduledateListAdapter scheduledateListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         mView = (CalendarCustomView)findViewById(R.id.custom_calendar);
        mView.mONOnLoadingButtonClickListener(this);
        scheduleList = (RecyclerView)findViewById(R.id.scheduleList);

        todayText = (TextView)findViewById(R.id.todayText);
        todayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    refreshScheduleList(currentDate.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
       /* scheduleList.addOnItemTouchListener(new RecyclerItemClickListener(HomeScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HomeScreen.this, AppointmentList.class);
                startActivity(intent);
            }
        }));
*/

        //refreshScheduleList();


    }

    @Override
    protected void onResume() {
        super.onResume();

        scheduleBeen = new ArrayList<>();
        scheduledateListAdapter =  new ScheduledateListAdapter(scheduleBeen,HomeScreen.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        scheduleList.setLayoutManager(mLayoutManager);
        scheduleList.setItemAnimator(new DefaultItemAnimator());
        scheduleList.setAdapter(scheduledateListAdapter);
        try {
            refreshScheduleList(currentDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
if(id == R.id.nav_profile){

}
      else  if(id == R.id.nav_schedule){


            startActivity(new Intent(HomeScreen.this,ScheduleListScreen.class));
        }

      else  if(id == R.id.nav_patient){


            startActivity(new Intent(HomeScreen.this,PatientList.class));
        }
      /*  if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoadingButtonClickListener(Date data) {
     //   Toast.makeText(HomeScreen.this, data.toString(), Toast.LENGTH_LONG).show();
        HomeHandler homeHandler = new HomeHandler();
        try {
            refreshScheduleList(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void refreshScheduleList(Date date) throws Exception {

        mView.cal.setTime(date);
      //  mView.cal.add(Calendar.MONTH, -1);
        mView.setUpCalendarAdapter();

       String[] dateArray = formatedTime(date).split(",");

        String daYid = dayToIDmap(dateArray[0]).toString();
        String Str_date = dateArray[1];
        String[] newDate =  Str_date.split("-");
        if(newDate.length > 0 ){
            Str_date = newDate[2] + "-"+newDate[1] + "-"+newDate[0];

        }
        HomeHandler homeHandler = new HomeHandler();
        scheduleBeen =  homeHandler.getDayscheduleList(Str_date,daYid);
        scheduledateListAdapter =  new ScheduledateListAdapter(scheduleBeen,HomeScreen.this);
        scheduleList.setAdapter(scheduledateListAdapter);
        mView.mAdapter.setSelected(date.getDate());

    }

    private String formatedTime(Date selecteedTime){



        SimpleDateFormat    sdf = new SimpleDateFormat("EE,dd-MM-yyyy");
        String formatedTime = sdf.format(selecteedTime);
        return formatedTime;
    }


    private String dayToIDmap(String dayKey){

        HashMap<String,String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("Mon","1");
        stringStringHashMap.put("Tue","2");
        stringStringHashMap.put("Wed","3");
        stringStringHashMap.put("Thu","4");
        stringStringHashMap.put("Fri","5");
        stringStringHashMap.put("Sat","6");
        stringStringHashMap.put("Sun","7");






        return stringStringHashMap.get(dayKey);
    }
}
