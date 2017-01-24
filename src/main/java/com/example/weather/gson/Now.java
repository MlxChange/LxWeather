package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Now {

    @SerializedName("tmp")
    public String tmp;//温度
    @SerializedName("cond")
    public Cond cond;
    @SerializedName("wind")
    public Wind wind;
    public class Cond{
        @SerializedName("txt")
        public String txt;//天气状况描述
    }
    public class Wind{
        @SerializedName("dir")
        public String dir;//风向
        @SerializedName("sc")
        public String sc;//风力等级
    }

}
