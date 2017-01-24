package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Aqi {

    public AqiCity city;

    public class AqiCity{
        @SerializedName("aqi")
        public String aqi;//空气质量指数
        @SerializedName("pm25")
        public String pm25;//PM2.5的指数
        @SerializedName("qlty")
        public String qlty;//空气质量
    }
}
