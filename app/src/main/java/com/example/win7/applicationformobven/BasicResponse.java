package com.example.win7.applicationformobven;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BasicResponse {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Coord coord;
    public Main main;
    public List<Weather> weather;
    public class Coord
    {
        private double lon;

        public double getLon() { return this.lon; }

        public void setLon(double lon) { this.lon = lon; }

        private double lat;

        public double getLat() { return this.lat; }

        public void setLat(double lat) { this.lat = lat; }
    }

    public class Weather
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String main;

        public String getMain() { return this.main; }

        public void setMain(String main) { this.main = main; }

        private String description;

        public String getDescription() { return this.description; }

        public void setDescription(String description) { this.description = description; }

        private String icon;

        public String getIcon() { return this.icon; }

        public void setIcon(String icon) { this.icon = icon; }
    }

    public class Main
    {
        private double temp;

        public double getTemp() { return this.temp; }

        public void setTemp(double temp) { this.temp = temp; }

        private int pressure;

        public int getPressure() { return this.pressure; }

        public void setPressure(int pressure) { this.pressure = pressure; }

        private int humidity;

        public int getHumidity() { return this.humidity; }

        public void setHumidity(int humidity) { this.humidity = humidity; }

        private double temp_min;

        public double getTempMin() { return this.temp_min; }

        public void setTempMin(double temp_min) { this.temp_min = temp_min; }

        private double temp_max;

        public double getTempMax() { return this.temp_max; }

        public void setTempMax(double temp_max) { this.temp_max = temp_max; }
    }

    public class Wind
    {
        private double speed;

        public double getSpeed() { return this.speed; }

        public void setSpeed(double speed) { this.speed = speed; }

        private int deg;

        public int getDeg() { return this.deg; }

        public void setDeg(int deg) { this.deg = deg; }
    }

    public class Clouds
    {
        private int all;

        public int getAll() { return this.all; }

        public void setAll(int all) { this.all = all; }
    }

    public class Sys
    {
        private int type;

        public int getType() { return this.type; }

        public void setType(int type) { this.type = type; }

        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private double message;

        public double getMessage() { return this.message; }

        public void setMessage(double message) { this.message = message; }

        private String country;

        public String getCountry() { return this.country; }

        public void setCountry(String country) { this.country = country; }

        private int sunrise;

        public int getSunrise() { return this.sunrise; }

        public void setSunrise(int sunrise) { this.sunrise = sunrise; }

        private int sunset;

        public int getSunset() { return this.sunset; }

        public void setSunset(int sunset) { this.sunset = sunset; }
    }

    public class RootObject {
        private Coord coord;

        public Coord getCoord() {
            return this.coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        private ArrayList<Weather> weather;

        public ArrayList<Weather> getWeather() {
            return this.weather;
        }

        public void setWeather(ArrayList<Weather> weather) {
            this.weather = weather;
        }

        private String base;

        public String getBase() {
            return this.base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        private Main main;

        public Main getMain() {
            return this.main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        private int visibility;

        public int getVisibility() {
            return this.visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        private Wind wind;

        public Wind getWind() {
            return this.wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        private Clouds clouds;

        public Clouds getClouds() {
            return this.clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        private int dt;

        public int getDt() {
            return this.dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        private Sys sys;

        public Sys getSys() {
            return this.sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }


        private int cod;

        public int getCod() {
            return this.cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }


    }

}
