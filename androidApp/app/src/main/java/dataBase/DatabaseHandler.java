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


    private static final String createSchedule= "CREATE TABLE schedule (schedule_id INTEGER PRIMARY KEY,schedule_name TEXT,start_time TEXT,end_time TEXT,start_date TEXT,end_date TEXT,capacity int);";
    private static final String createScheduleDay= "CREATE TABLE schedule_day (schedule_id INTEGER, day_id INTEGER,FOREIGN KEY(schedule_id) REFERENCES schedule(schedule_id), PRIMARY KEY (schedule_id, day_id));";
    private static final String createAppointment= "CREATE TABLE appointment (appointment_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,schedule_id INTEGER,appointment_date TEXT,client_id INTEGER,status TEXT,token_no TEXT,token_datetime TEXT,token_reorder_time TEXT,availed_time TEXT);";
    private static final String createpatinet= "CREATE TABLE patient(patient_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, patient_name TEXT, father_name TEXT,age INTEGER,contact_no TEXT);";


    private static final String patinet= "INSERT INTO \"patient\" VALUES(1,'Hassaan Khan','Mohsin',27,'03343673008');\n";
    private static final String patinet1 ="INSERT INTO \"patient\" VALUES(2,'Zohair Abidi','Askari',27,'03333601437');\n" ;
    private static final String patinet2 = "INSERT INTO \"patient\" VALUES(3,'Saad Surya','Aslam',24,'03343535203');";






    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private SQLiteDatabase  database;

    @Override
    public void onCreate(SQLiteDatabase db) {

        database = db;
       createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            database = sqLiteDatabase;
            database.execSQL(patinet1);
            database.execSQL(patinet2);
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



}
