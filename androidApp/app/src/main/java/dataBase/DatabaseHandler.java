package dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali.Zohair on 8/19/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "docsAppDB";


    private static final String createSchedule= "CREATE TABLE schedule (schedule_id INTEGER PRIMARY KEY,schedule_name TEXT,start_time TEXT,end_time TEXT,start_date TEXT,end_date TEXT,capacity int);";
    private static final String createScheduleDay= "CREATE TABLE schedule_day (day_id INTEGER PRIMARY KEY,schedule_id INTEGER,FOREIGN KEY(schedule_id) REFERENCES schedule(schedule_id));";




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

    }



    private void createTables()
    {
        database.execSQL(createSchedule);
        database.execSQL(createScheduleDay);
    }



}
