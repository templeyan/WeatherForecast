package com.ymy.weatherforecast.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ymy on 2017/3/19.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;//记录市的名字
    private int cityCode;//记录市的编号
    private int provinceID;//记录当前市所属省的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
