package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import java.util.ArrayList;

import bussines.HomeHandler;

public class NewAppoint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appoint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getIntent().getSerializableExtra("ScheduleBean");

        ImageButton newCustomer = (ImageButton)findViewById(R.id.add_patient_btn);
        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NewAppoint.this,Add_member.class));

            }
        });

        AutoCompleteTextView patientName = (AutoCompleteTextView) findViewById(R.id.edittext_name);
        HomeHandler homeHandler = new HomeHandler();
        try {

            patientName.setThreshold(1);
        ArrayList<PatientBean> patientBeen =  homeHandler.getPatient();

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
