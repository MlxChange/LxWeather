package com.example.weather.gson;

import com.example.weather.db.City;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;//城市名称
    @SerializedName("id")
    public String weatherID;//城市ID

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;//更新时间
    }

}
