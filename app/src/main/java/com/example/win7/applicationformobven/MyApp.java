package com.example.win7.applicationformobven;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication{
    public static SharedPreferences sharedPreferences;
    public static String SHARED_PREFERENCES_NAME = "myapp";
    public static SharedPreferences.Editor editor;
    private static MyApp myApp;
    public static MyApp getMyApp() {
        return myApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        sharedPreferences = getSharedPreferences(MyApp.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public static SharedPreferences sharedpreferences;

    //sharedpref'den veri çekme işlemini buradan yönetiyoruz
    public static String getSharedPrefences(String key, String value){

        sharedPreferences = getContext().getSharedPreferences(MyApp.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        return sharedPreferences.getString(key, value);
    }
    public static Context getContext() {
        //  return instance.getApplicationContext();
        return MyApp.getMyApp().getApplicationContext();
    }
}
