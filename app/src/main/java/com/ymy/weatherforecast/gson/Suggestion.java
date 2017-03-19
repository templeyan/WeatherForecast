package com.ymy.weatherforecast.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ymy on 2017/3/19.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comform comform;
    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;
    public  class Comform {
        @SerializedName("txt")
        public String info;
    }

    public  class CarWash {
        @SerializedName("txt")
        public String info;

    }

    public  class Sport {
        @SerializedName("txt")
        public String info;
    }
}
