package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Weather {

    public String status;//接口状态
    public Basic basic;//基础信息
    public Aqi aqi;//空气质量信息
    @SerializedName("daily_forecast")
    public List<Daily_forecast> daily_forecast;//未来七天天气信息
    public Now now;//现在天气信息
    @SerializedName("suggestion")
    public Suggesstion suggesstion;//各种建议

}
