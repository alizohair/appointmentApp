package com.hass.ali.doctorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import bussines.HomeHandler;

public class PatientList extends AppCompatActivity {
    private RecyclerView patientList;
    PatinetListAdapter patientListAdapter = null;
    ArrayList<PatientBean> patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientList.this,Add_member.class));
            }
        });


        patientList = (RecyclerView)findViewById(R.id.patient_list);

        try {

            patientListAdapter = new PatinetListAdapter(patient,PatientList.this);
        } catch (Exception e) {
            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        patientList.setLayoutManager(mLayoutManager);
        patientList.setItemAnimator(new DefaultItemAnimator());
        patientList.setAdapter(patientListAdapter);






    }



    @Override
    protected void onResume() {
        super.onResume();

        HomeHandler homeHandler = new HomeHandler();
        try {

           patient = homeHandler.getPatient();
            //patientListAdapter = new PatinetListAdapter(patient,PatientList.this);
            patientListAdapter.updateList(patient);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
