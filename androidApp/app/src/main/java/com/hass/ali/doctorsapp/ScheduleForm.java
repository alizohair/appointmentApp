package com.hass.ali.doctorsapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dataBase.DBConnection;


public class ScheduleForm extends AppCompatActivity {
    TextView timeFrom;
    TextView timeTo;
Button saveBtn;
    TextView dateFrom;
    TextView dateTo;
    TextView name;
    TextView capacityET;
   CheckBox CbMon,CbTue,CbWed,CbThu,CbFri,CbSat,CbSun;

    private int mYear, mMonth, mDay, mHour, mMinute;

  //  SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        CbMon = (CheckBox)findViewById(R.id.check_mon);
                CbTue = (CheckBox)findViewById(R.id.check_tue);
        CbWed = (CheckBox)findViewById(R.id.check_wed);
                CbThu = (CheckBox)findViewById(R.id.check_thu);
        CbFri = (CheckBox)findViewById(R.id.check_fri);
                CbSat = (CheckBox)findViewById(R.id.check_sat);
        CbSun = (CheckBox)findViewById(R.id.check_sun);





        saveBtn = (Button)findViewById(R.id.saveBtn);








      //  SQLiteDatabase db = databaseHandler.getWritableDatabase();



        name = (TextView) findViewById(R.id.edittext_name);
        capacityET = (TextView) findViewById(R.id.capacity_Ed);
        dateFrom = (TextView) findViewById(R.id.textView_startDate);
        dateTo = (TextView) findViewById(R.id.textView_endDate);
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


 dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });




         timeFrom = (TextView) findViewById(R.id.textview_time_from);
        timeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeFrom.setText(formatedTime( String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute)));
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        timeTo= (TextView) findViewById(R.id.textview_time_to);
        timeTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {



                        timeTo.setText(formatedTime( String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute)));
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,String> scheduleData = new HashMap<String, String>();
                scheduleData.put("name",name.getText().toString());
                scheduleData.put("timeTo",timeTo.getText().toString());
                scheduleData.put("timeFrom",timeFrom.getText().toString());
                scheduleData.put("dateTo",dateTo.getText().toString());
                scheduleData.put("dateFrom",dateFrom.getText().toString());
                scheduleData.put("capacity",capacityET.getText().toString());



                ArrayList daysList = new ArrayList();
                if(CbMon.isChecked()){
                    daysList.add("1");

                }
                if(CbTue.isChecked()){

                    daysList.add("2");
                }
                if(CbWed.isChecked()){
                    daysList.add("3");
                }
                if(CbThu.isChecked()){

                    daysList.add("4");
                }
                if(CbFri.isChecked()){
                    daysList.add("5");

                }
                if(CbSat.isChecked()){

                    daysList.add("6");
                }
                if(CbSun.isChecked()){
                    daysList.add("7");

                }

                try {
                    saveRecords(scheduleData,daysList);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private String formatedTime(String selecteedTime){


        String timeString =selecteedTime;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date time = null;
        try {
            time = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf = new SimpleDateFormat("hh:mm a");
        String formatedTime = sdf.format(time);
        return formatedTime;
    }


    private void saveRecords(HashMap<String,String> scheduleData, ArrayList arrayList ) throws Exception{

     //   getNewScheduleId();


      String scheduleID = String.valueOf(getNewScheduleId());
        ContentValues scheduleCV = new ContentValues();
        scheduleCV.put("schedule_id",scheduleID);
        scheduleCV.put("schedule_name", scheduleData.get("name"));
        scheduleCV.put("start_time", scheduleData.get("timeTo"));
        scheduleCV.put("end_time", scheduleData.get("timeFrom"));
        scheduleCV.put("start_date", scheduleData.get("dateTo"));
        scheduleCV.put("end_date", scheduleData.get("dateFrom"));
        scheduleCV.put("capacity", scheduleData.get("capacity"));
       // SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        DBConnection.setBeginTransaction(true);
       // db.beginTransaction();
        DBConnection.insertRow("schedule", scheduleCV);


        for(int a = 0; a < arrayList.size();a++){

            ContentValues scheduleDaysCV = new ContentValues();
            scheduleDaysCV.put("schedule_id",scheduleID);
            scheduleDaysCV.put("day_id", String.valueOf(arrayList.get(a)));
            DBConnection.insertRow("schedule_day", scheduleDaysCV);
           // db.insert("schedule_day", null, scheduleDaysCV);

}
        DBConnection.committTransaction();

        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();


    }


      private int getNewScheduleId() throws Exception{

        //  SQLiteDatabase  db = databaseHandler.getWritableDatabase();
          String count = "";
          String selectQuery = " select count (*) from schedule;";
       //   db = databaseHandler.getWritableDatabase();

          Cursor cursor = DBConnection.rawQuery(selectQuery, null);

          if (cursor.moveToFirst()) {

              count = cursor.getString(0);

          }
         //     db.close();

return Integer.parseInt(count) + 1;


    }




}
