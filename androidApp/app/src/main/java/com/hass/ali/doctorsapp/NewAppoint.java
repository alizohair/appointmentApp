package com.hass.ali.doctorsapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


import bussines.DateFormater;
import bussines.HomeHandler;
import dataBase.DBConnection;
import extensions.ArrayListExtended;

public class NewAppoint extends AppCompatActivity {
    AutoCompleteTextView patientName;
    private static final int REQUEST_GET_MAP_LOCATION = 0;
    TextView dateText;
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<ScheduleBean> scheduleBeen;
    ArrayList<PatientBean> patientBeen;
    Spinner spinner;
    Button saveBtn;
    HomeHandler homeHandler;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appoint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final ScheduleBean scheBean =   (ScheduleBean)getIntent().getSerializableExtra("ScheduleBean");
saveBtn = (Button)findViewById(R.id.saveBtn);

        patientName = (AutoCompleteTextView) findViewById(R.id.edittext_name);


        ImageButton newCustomer = (ImageButton)findViewById(R.id.add_patient_btn);

         dateText = (TextView)findViewById(R.id.dateText);

        spinner = (Spinner)findViewById(R.id.spinner);
        homeHandler = new HomeHandler();

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAppoint.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });



        dateText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                {
                    HomeHandler homeHandler = new HomeHandler();
                    try {
                    scheduleBeen =  homeHandler.getscheduleListFromDay(DateFormater.getDayFromDateString(s.toString()));
                        ArrayList<String> scheduleName = new ArrayList<String>();
                        for(int a = 0;a< scheduleBeen.size() ;a++){
                            scheduleName.add(scheduleBeen.get(a).getScheduleName());
                        }

                        if(scheduleBeen.size()<1){
                            Toast.makeText(NewAppoint.this, "Date no available", Toast.LENGTH_SHORT).show();
                        }
                        spinnerArrayAdapter = new ArrayAdapter<String>(NewAppoint.this, android.R.layout.simple_spinner_item, scheduleName); //selected item will look like a spinner set from XML
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerArrayAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NewAppoint.this, "s: "+e, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


       String[] newDate =  scheBean.getCurrentDate().split("-");
        String date = scheBean.getCurrentDate();
        if(newDate.length > 0){
            date = newDate[2] + "-"+newDate[1]+"-"+newDate[0];


        }
        dateText.setText(date);

        for (int position = 0; position < scheduleBeen.size(); position++) {
            if (scheBean.getScheduleID().equals(scheduleBeen.get(position).scheduleID))
                ((Spinner) spinner).setSelection(position);
        }




        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(NewAppoint.this,Add_member.class), REQUEST_GET_MAP_LOCATION);

              //  startActivity();

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String appointmentId = null;
                try {
                    appointmentId = String.valueOf(homeHandler.getNewId("appointment"));
                    ContentValues appointmentCv = new ContentValues();

                    appointmentCv.put("appointment_id",appointmentId);
                    appointmentCv.put("schedule_id",scheduleBeen.get(spinner.getSelectedItemPosition()).getScheduleID());

                    String[] newDate = dateText.getText().toString().split("-");
                    String date = dateText.getText().toString();
                    if(newDate.length > 0){
                        date =  newDate[2]+"-"+newDate[1]+"-"+newDate[0];

                    }

                    appointmentCv.put("appointment_date",date);

                    PatientBean selectedPatient = ((DepartmentArrayAdapter)patientName.getAdapter()).getSelectedPatient();
                    appointmentCv.put("client_id",selectedPatient.getPatientID());
                    appointmentCv.put("status","pending");


                    if(DBConnection.insertRow("appointment", appointmentCv)){

                        Toast.makeText(NewAppoint.this, "appointment save for date "+dateText.getText(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {

                        Toast.makeText(NewAppoint.this, "appointment saving fail for date"+dateText.getText(), Toast.LENGTH_SHORT).show();

                    }
                }
                catch (Exception e){


                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
            String patienId = data.getStringExtra("patienId");
            String name = data.getStringExtra("name");
            patientName.setText(name);

        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        HomeHandler homeHandler = new HomeHandler();
        try {

            patientName.setThreshold(1);
         patientBeen =  homeHandler.getPatient();

            DepartmentArrayAdapter   mDepartmentArrayAdapter = new DepartmentArrayAdapter(NewAppoint.this, R.layout.simple_text_view, patientBeen);
            patientName.setAdapter(mDepartmentArrayAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





}
