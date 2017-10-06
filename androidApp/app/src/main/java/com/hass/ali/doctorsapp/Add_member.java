package com.hass.ali.doctorsapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dataBase.DBConnection;

public class Add_member extends AppCompatActivity {

    EditText contact_no,age,f_name,edittext_name;
    Button saveBtn;

    String str_contact_no;
    String str_age;
    String str_f_name;
    String str_edittext_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        contact_no = (EditText) findViewById(R.id.contact_no);
        age = (EditText) findViewById(R.id.age);
        f_name = (EditText) findViewById(R.id.f_name);
        edittext_name = (EditText) findViewById(R.id.edittext_name);


        str_contact_no = contact_no.getText().toString();
        str_age = age.getText().toString();
        str_f_name = f_name.getText().toString();
        str_edittext_name = edittext_name.getText().toString();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!str_contact_no.equalsIgnoreCase("") && !str_age.equalsIgnoreCase("") &&
                        !str_f_name.equalsIgnoreCase("") && !str_edittext_name.equalsIgnoreCase("")){
                    String patientID = null;
                    try {
                        patientID = String.valueOf(getNewPatientId());
                    ContentValues patieantCv = new ContentValues();

                    patieantCv.put("patient_id",patientID);


                            patieantCv.put("patient_name",str_edittext_name);
                            patieantCv.put("contact_no",str_contact_no);
                            patieantCv.put("father_name",str_f_name);
                            patieantCv.put("age",str_age);

                        DBConnection.insertRow("patient", patieantCv);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                }else {
                    Toast.makeText(Add_member.this, "Fill all data before saving", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private int getNewPatientId() throws Exception{

        //  SQLiteDatabase  db = databaseHandler.getWritableDatabase();
        String count = "";
        String selectQuery = " select count (*) from patient;";
        //   db = databaseHandler.getWritableDatabase();

        Cursor cursor = DBConnection.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            count = cursor.getString(0);

        }
        //     db.close();

        return Integer.parseInt(count) + 1;


    }

}
