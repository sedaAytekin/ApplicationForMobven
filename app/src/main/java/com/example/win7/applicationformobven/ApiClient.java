package com.example.win7.applicationformobven;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Request request;
    private static String oldNetworkType = null;
    private static APIInterface service;

    //10 Saniye Timeout süresi veriyor, default bu
    public synchronized static APIInterface webService() {

     //   String networkStatus = getNetworkType();
        boolean resetFlag = false;

//        if(!networkStatus.equals(oldNetworkType)){
//            oldNetworkType = networkStatus;
//            resetFlag = true;
//        }
        if(resetFlag){
            retrofit = null;
        }

        if (retrofit == null) {
            service = null;
            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            httpBuilder.readTimeout(10, TimeUnit.SECONDS);
            httpBuilder.writeTimeout(10,TimeUnit.SECONDS);

            httpBuilder.addInterceptor(chain -> {
                Response response = chain.proceed(chain.request());

                Request.Builder requestBuilder = chain.request().newBuilder();

                return chain.proceed(requestBuilder.build());
            });
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpBuilder.build())
                    .build();
        }
        if(service == null){
            service = retrofit.create(APIInterface.class);
        }

        return service;
    }


    private static String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown Type";
        }
    }
}
