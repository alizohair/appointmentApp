package com.hass.ali.doctorsapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
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

import bussines.HomeHandler;
import dataBase.DBConnection;


public class ScheduleForm extends AppCompatActivity {
    TextView timeFrom;
    TextView timeTo;
    Button saveBtn,updateBtn;
    TextView dateFrom;
    TextView dateTo;
    TextView name;
    TextView capacityET;
    CheckBox CbMon,CbTue,CbWed,CbThu,CbFri,CbSat,CbSun;
    ScheduleBean scheBean;

    String Schedule_ID = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        scheBean =   (ScheduleBean) getIntent().getSerializableExtra("ScheduleBean");

        CbMon = (CheckBox)findViewById(R.id.check_mon);
        CbTue = (CheckBox)findViewById(R.id.check_tue);
        CbWed = (CheckBox)findViewById(R.id.check_wed);
        CbThu = (CheckBox)findViewById(R.id.check_thu);
        CbFri = (CheckBox)findViewById(R.id.check_fri);
        CbSat = (CheckBox)findViewById(R.id.check_sat);
        CbSun = (CheckBox)findViewById(R.id.check_sun);





        saveBtn = (Button)findViewById(R.id.saveBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);

        saveBtn.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.GONE);






      //  SQLiteDatabase db = databaseHandler.getWritableDatabase();



        name = (TextView) findViewById(R.id.edittext_name);
        capacityET = (TextView) findViewById(R.id.capacity_Ed);
        dateFrom = (TextView) findViewById(R.id.textView_startDate);
        dateTo = (TextView) findViewById(R.id.textView_endDate);
        timeFrom = (TextView) findViewById(R.id.textview_time_from);
        timeTo= (TextView) findViewById(R.id.textview_time_to);
       if(scheBean!=null){

           saveBtn.setVisibility(View.GONE);
           updateBtn.setVisibility(View.VISIBLE);

           Schedule_ID = scheBean.getScheduleID();
           name.setText(scheBean.getScheduleName());
           capacityET.setText(scheBean.getCapacity());
           dateFrom.setText(scheBean.getScheduledateFrom());
           dateTo.setText(scheBean.getScheduledateto());
           timeFrom.setText(scheBean.getScheduleTimeFrom());
           timeTo.setText(scheBean.getScheduleTimeTo());

           for(int a = 0;a<scheBean.getDays().size();a++){

               if(scheBean.getDays().get(a).equalsIgnoreCase("1")){
                   CbMon.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("2")){
                   CbTue.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("3")){
                   CbWed.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("4")){
                   CbThu.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("5")){
                   CbFri.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("6")){
                   CbSat.setChecked(true);
               }else if(scheBean.getDays().get(a).equalsIgnoreCase("7")){
                   CbSun.setChecked(true);
               }

           }

       }




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


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_timeTo = timeTo.getText().toString();
                String  str_timeFrom = timeFrom.getText().toString();
                String str_dateTO = dateTo.getText().toString();
                String str_dateFrom = dateFrom.getText().toString();


                if(str_timeTo == "" || str_timeFrom == "" || str_dateTO == "" || str_dateFrom == "" ){

                    Toast.makeText(ScheduleForm.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return ;

                }
                try {
                    SimpleDateFormat parser = new SimpleDateFormat("hh:mm a");
                    Date timeTO = parser.parse(str_timeTo);
                    Date timeFROM = parser.parse(str_timeFrom);

                    if (timeFROM.compareTo(timeTO) > 0) {
                        Toast.makeText(ScheduleForm.this, "time  to cannot be before time from ", Toast.LENGTH_SHORT).show();
                        return ;
                    }


                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


                    Date dateTO = sdf.parse(str_dateTO);
                    Date dateFROM = sdf.parse(str_dateFrom);

                    if (dateFROM.compareTo(dateTO) > 0) {
                        Toast.makeText(ScheduleForm.this, "date to cannot be before date from ", Toast.LENGTH_SHORT).show();
                        return ;
                    }





                } catch (ParseException e) {
                    e.printStackTrace();
                }
                HashMap<String,String> scheduleData = new HashMap<String, String>();
                scheduleData.put("name",name.getText().toString());
                scheduleData.put("timeTo",str_timeTo);
                scheduleData.put("timeFrom",str_timeFrom);
                scheduleData.put("dateTo",str_dateTO);
                scheduleData.put("dateFrom",str_dateFrom);
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

                    HomeHandler homeHandler = new HomeHandler();
                    try {

                        if(homeHandler.deleteSchedule(Schedule_ID))
                        {

                            saveRecords(scheduleData,daysList);
                         //   Toast.makeText(ScheduleForm.this, "Schedule deleted",
                           //         Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ScheduleForm.this, "could not update schedule, already appointment taken",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Toast.makeText(ScheduleForm.this, "delete record before you update schedule", Toast.LENGTH_SHORT).show();






            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String str_timeTo = timeTo.getText().toString();
              String  str_timeFrom = timeFrom.getText().toString();
              String str_dateTO = dateTo.getText().toString();
              String str_dateFrom = dateFrom.getText().toString();


                if(str_timeTo == "" || str_timeFrom == "" || str_dateTO == "" || str_dateFrom == "" ){

                    Toast.makeText(ScheduleForm.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return ;

                }
                try {
                SimpleDateFormat parser = new SimpleDateFormat("hh:mm a");
                Date timeTO = parser.parse(str_timeTo);
                Date timeFROM = parser.parse(str_timeFrom);

                    if (timeFROM.compareTo(timeTO) > 0) {
                        Toast.makeText(ScheduleForm.this, "time  to cannot be before time from ", Toast.LENGTH_SHORT).show();
                        return ;
                    }


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


                    Date dateTO = sdf.parse(str_dateTO);
                    Date dateFROM = sdf.parse(str_dateFrom);

                if (dateFROM.compareTo(dateTO) > 0) {
                    Toast.makeText(ScheduleForm.this, "date to cannot be before date from ", Toast.LENGTH_SHORT).show();
                    return ;
                }





                } catch (ParseException e) {
                    e.printStackTrace();
                }
                HashMap<String,String> scheduleData = new HashMap<String, String>();
                scheduleData.put("name",name.getText().toString());
                scheduleData.put("timeTo",str_timeTo);
                scheduleData.put("timeFrom",str_timeFrom);
                scheduleData.put("dateTo",str_dateTO);
                scheduleData.put("dateFrom",str_dateFrom);
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
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void saveScheduleForm(){


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
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }





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

        if(Schedule_ID == null){

             Schedule_ID = String.valueOf(getNewScheduleId());
        }
        ContentValues scheduleCV = new ContentValues();
        scheduleCV.put("schedule_id",Schedule_ID);
        scheduleCV.put("schedule_name", scheduleData.get("name"));
        scheduleCV.put("start_time", scheduleData.get("timeTo"));
        scheduleCV.put("end_time", scheduleData.get("timeFrom"));
        scheduleCV.put("start_date", scheduleData.get("dateFrom"));
        scheduleCV.put("end_date", scheduleData.get("dateTo"));
        scheduleCV.put("capacity", scheduleData.get("capacity"));
       // SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        DBConnection.setBeginTransaction(true);
       // db.beginTransaction();
        DBConnection.insertRow("schedule", scheduleCV);


        for(int a = 0; a < arrayList.size();a++){

            ContentValues scheduleDaysCV = new ContentValues();
            scheduleDaysCV.put("schedule_id",Schedule_ID);
            scheduleDaysCV.put("day_id", String.valueOf(arrayList.get(a)));
            DBConnection.insertRow("schedule_day", scheduleDaysCV);
           // db.insert("schedule_day", null, scheduleDaysCV);

}
        DBConnection.committTransaction();

        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();


    }





  private void UPDATERecords(HashMap<String,String> scheduleData, ArrayList arrayList ) throws Exception{
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
