package com.example.win7.applicationformobven.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListModel {
    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    @SerializedName("id")
    @Expose
    private String cityId;
    @SerializedName("name")
    @Expose
    private String name;

}
