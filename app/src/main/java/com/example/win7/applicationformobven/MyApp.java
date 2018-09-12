package com.example.win7.applicationformobven;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication{
    public static SharedPreferences sharedPreferences;
    public static String SHARED_PREFERENCES_NAME = "myapp";

    private static MyApp myApp;
    private static Context context;

    public static MyApp getMyApp() {
        return myApp;
    }
    public MyApp() {
        super();
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


    }


}
