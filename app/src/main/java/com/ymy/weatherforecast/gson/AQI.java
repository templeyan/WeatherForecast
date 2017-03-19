package com.ymy.weatherforecast.gson;

/**
 * Created by ymy on 2017/3/19.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
