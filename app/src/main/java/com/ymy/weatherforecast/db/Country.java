package com.ymy.weatherforecast.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ymy on 2017/3/19.
 */

public class Country extends DataSupport {
    private int id;
    private String countryName;//记录县的名字
    private String weatherID;//记录县所对应的的id
    private int cityId;//记录当前所属市的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(String weatherID) {
        this.weatherID = weatherID;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
