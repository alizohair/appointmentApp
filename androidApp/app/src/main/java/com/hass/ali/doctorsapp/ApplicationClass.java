package com.hass.ali.doctorsapp;

import android.app.Application;
import android.content.Context;


/**
 * Created by ali.zohair on 9/16/2017.
 */

public class ApplicationClass extends Application {

    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();


    }

}
