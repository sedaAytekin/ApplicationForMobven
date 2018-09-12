package com.example.win7.applicationformobven;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.win7.applicationformobven.Camera.CameraFragment;
import com.example.win7.applicationformobven.HomePage.HomePageFragment;
import com.example.win7.applicationformobven.HomePage.ViewPagerFragment;
import com.example.win7.applicationformobven.Response.CityListModel;
import com.example.win7.applicationformobven.SettingsPage.SettingsPageFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ResponseViewModel extends ViewModel {

    Context context;

    public MutableLiveData<Double> getMinimumTemp() {
        return minimumTemp;
    }

    private MutableLiveData<Double> minimumTemp = new MutableLiveData<>();

    public MutableLiveData<List<CityListModel>> getCityList() {
        return cityList;
    }

    private MutableLiveData<List<CityListModel>> cityList = new MutableLiveData<>();

    public MutableLiveData<String> getCity() {
        return city;
    }

    private MutableLiveData<String> city = new MutableLiveData<>();

    public MutableLiveData<String> getWeatherIcon() {
        return weatherIcon;
    }

    private MutableLiveData<String> weatherIcon = new MutableLiveData<>();

    public MutableLiveData<String> getDescription() {
        return description;
    }

    private MutableLiveData<String> description = new MutableLiveData<>();

    public MutableLiveData<Float> getBatteryState() {
        return batteryState;
    }

    private MutableLiveData<Float> batteryState = new MutableLiveData<>();

    public MutableLiveData<String> getSelectedCity() {
        return selectedCity;
    }

    public MutableLiveData<String> selectedCity = new MutableLiveData<>();

    public MutableLiveData<Integer> getPositionDefault() {
        return positionDefault;
    }

    private MutableLiveData<Integer> positionDefault = new MutableLiveData<>();

    public MutableLiveData<Double> getMaximumTemp() {
        return maximumTemp;
    }

    private MutableLiveData<Double> maximumTemp = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsWifi() {
        return isWifi;
    }

    public MutableLiveData<Boolean> isWifi = new MutableLiveData<>();
    public static SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    public SharedPreferences.Editor editor;
    public ResponseViewModel(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        getBatteryLevel(context);
        getNetworkType(context);
        sharedPreferences = context.getSharedPreferences(MyApp.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        //sharedpref te kayıtlı city varsa onun bilgilerini getiriyoruz, yoksa default olarak izmir getiriyoruz.
        if (sharedPreferences.getString("cityName","") != null && !sharedPreferences.getString("cityName","").equals("")){
            getWeather(sharedPreferences.getString("cityName",""));
        }else{
            getWeather("Izmir");
        }

        loadJSONFromAsset(context);
    }
    public void searchCityClick(String cityName) {
        sharedPreferences = context.getSharedPreferences(MyApp.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (cityName != null){
            ViewPagerFragment homePageFragment = new ViewPagerFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, homePageFragment);
            transaction.commit();
            //appten çıkıp girildiğinde de son seçileni getirmesi için sharedpref'e kaydediyoruz.
            editor.putString("cityName", selectedCity.getValue());
            editor.apply();
            getWeather(cityName);
        }else{
            Toast.makeText(context, "Lütfen şehir seçiniz.", Toast.LENGTH_SHORT).show();
        }


    }
    public float getBatteryLevel(Context context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
            return 50.0f;
        }
        batteryState.setValue(((float)level / (float)scale) * 100.0f);

        return ((float)level / (float)scale) * 100.0f;
    }
    //assets klasörüne eklediğimiz json dosyasını okuyoruz.
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("city_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //citylist e tüm şehirleri ekliyoruz
            ArrayList<CityListModel> yourArray = new Gson().fromJson(json, new TypeToken<List<CityListModel>>(){}.getType());
            cityList.setValue(yourArray);
            for (int i = 0;i<yourArray.size();i++){
                if (yourArray.get(i).getName().equals("Izmir")){
                    positionDefault.setValue(i);
                }
            }

        return json;

    }


    private String getNetworkType(Context context){
        String networkType = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            //wifi bağlı ise setediyoruz.
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                networkType = "Wifi";
                isWifi.setValue(true);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                networkType = "Mobile";
                isWifi.setValue(false);
            }
        } else {
            networkType = "Not Connected";
            isWifi.setValue(false);
        }
        return networkType;
    }
    private void getWeather(String cityName) {

        //metric:celcius response için, lang: description değerinin türkçe gelmesi için;
        ApiClient.webService().session(cityName,"2b038fe952a28c53e6f9986f01630ef0","metric", "tr").enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                BasicResponse body = response.body();
                if (body != null) {
                    minimumTemp.setValue(body.main.getTempMin());
                    maximumTemp.setValue(body.main.getTempMax());
                    city.setValue(body.getName());
                    description.setValue(body.weather.get(0).getDescription());
                    weatherIcon.setValue("http://openweathermap.org/img/w/" + response.body().weather.get(0).getIcon() + ".png");

                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Log.d("dfd", "sd");
            }
        });
    }
    public void bottomMenuClick(String pageName) {
        if (pageName != null){
            if (pageName.equals("home")){
                ViewPagerFragment homePageFragment = new ViewPagerFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, homePageFragment);
                transaction.commit();
            } else if (pageName.equals("camera")){
                CameraFragment cameraFragment = new CameraFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, cameraFragment);
                transaction.commit();
            } else {
                SettingsPageFragment settingsPageFragment = new SettingsPageFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, settingsPageFragment);
                transaction.commit();
            }
        }


    }
}
