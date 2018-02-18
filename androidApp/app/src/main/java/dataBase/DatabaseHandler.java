package dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali.Zohair on 8/19/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "docsAppDB";


    private static final String createSchedule= "CREATE TABLE schedule (schedule_id INTEGER PRIMARY KEY,schedule_name TEXT,start_time Datetime,end_time Datetime,start_date Datetime,end_date Datetime,capacity int);";
    private static final String createScheduleDay= "CREATE TABLE schedule_day (schedule_id INTEGER, day_id INTEGER,FOREIGN KEY(schedule_id) REFERENCES schedule(schedule_id), PRIMARY KEY (schedule_id, day_id));";
    private static final String createAppointment= "CREATE TABLE appointment " + "(appointment_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,schedule_id INTEGER," + "appointment_date Datetime,client_id INTEGER,status TEXT,token_no TEXT,token_datetime Datetime," + "token_reorder_time Datetime,availed_time Datetime);";
    private static final String createpatinet= "CREATE TABLE patient(patient_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, patient_name TEXT, father_name TEXT,age INTEGER,contact_no TEXT);";
    private static final String createDoctor_clinic = "CREATE TABLE doctor_clinic(doctor_id INTEGER NOT NULL, clinic_id INTEGER NOT NULL, FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id), FOREIGN KEY(clinic_id) REFERENCES clinic(clinic_id));";
    private static final String createDoctor = "CREATE TABLE doctor(doctor_id INTEGER NOT NULL PRIMARY KEY,doctor_name TEXT,username TEXT,password TEXT);";
    private static final String createClinic = "CREATE TABLE clinic(clinic_id INTEGER NOT NULL PRIMARY KEY, clinic_name TEXT, address TEXT);";


    private static final String schedule = "insert into schedule( schedule_id, schedule_name, start_time, end_time, start_date, end_date, capacity) values(1, 'Afternoon', '13:00:00', '16:00:00', '2017-09-01', '2018-09-01', 80);";
    private static final String patinet= "INSERT INTO patient VALUES(1,'Hassaan Khan','Mohsin',27,'03343673008');";
    private static final String patinet1 ="INSERT INTO patient VALUES(2,'Zohair Abidi','Askari',27,'03333601437');" ;
    private static final String patinet2 = "INSERT INTO patient VALUES(3,'Saad Surya','Aslam',24,'03343535203');";
    private static final String doctor = "INSERT INTO doctor VALUES(1,'Gohar Aalam','admin','admin');";
    private static final String clinic = "INSERT INTO clinic VALUES(1,'Imam Clinic','5 star chorangi');";
    private static final String doctor_clinic = "INSERT INTO doctor_clinic VALUES(1,1);";
    private static final String schedule1 = "insert into schedule( schedule_id, schedule_name, start_time, end_time, start_date, end_date, capacity) values(2, 'Night', '19:00:00', '22:00:00', '2017-09-01','2018-09-01', 50);";
    private static final String day1 = "INSERT INTO schedule_day VALUES(1,'1');";
    private static final String day2= "INSERT INTO schedule_day VALUES(2,'2');";
    private static final String day3 = "INSERT INTO schedule_day VALUES(2,'4');";
    private static final String day4= "INSERT INTO schedule_day VALUES(2,'6');";
    private static final String day5 = "INSERT INTO schedule_day VALUES(1,'3');";
    private static final String da6 = "INSERT INTO schedule_day VALUES(1,'5');";
    private static final String day7 = "INSERT INTO schedule_day VALUES(1,'7');";
    private static final String day8 = "INSERT INTO schedule_day VALUES(2,'7');";













    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private SQLiteDatabase  database;

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        database = db;
       createTables();
        updateQuery();

        }catch (Exception e ){
            e.printStackTrace();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            database = sqLiteDatabase;

        }catch (Exception e ){
            e.printStackTrace();

        }

    }



    private void createTables()
    {
        database.execSQL(createSchedule);
        database.execSQL(createScheduleDay);
        database.execSQL(createAppointment);
    }

    private void updateQuery(){

        database.execSQL(createpatinet);
        database.execSQL(createDoctor);
        database.execSQL(createClinic);
        database.execSQL(createDoctor_clinic);


       /* database.execSQL(patinet);
        database.execSQL(patinet1);
        database.execSQL(patinet2);


        database.execSQL(doctor);
        database.execSQL(clinic);
        database.execSQL(doctor_clinic);

        database.execSQL(schedule);
        database.execSQL(schedule1);

        database.execSQL(day1);
        database.execSQL(day2);
        database.execSQL(day3);
        database.execSQL(day4);
        database.execSQL(day5);
        database.execSQL(da6);
        database.execSQL(day7);
        database.execSQL(day8);*/




    }


}
