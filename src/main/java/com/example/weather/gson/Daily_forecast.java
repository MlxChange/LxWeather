package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Daily_forecast {

    @SerializedName("astro")
    public Astro astro;//天文数值
    @SerializedName("pop")
    public String pop;//降水概率
    @SerializedName("vis")
    public String Vis;//能见度
    public Wind wind;
    @SerializedName("hum")
    public String Hum;//相对湿度
    @SerializedName("cond")
    public Cond cond;//天气状况
    public Tmp tmp;


    //天文数值
    public class Astro{

        @SerializedName("mr")
        public String mr;//月升时间
        @SerializedName("ms")
        public String ms;//月落时间
        @SerializedName("sr")
        public String sr;//日升时间
        @SerializedName("ss")
        public String ss;//日落时间
    }
    public class Tmp{
        @SerializedName("max")
        public String max;//最高温度
        @SerializedName("min")
        public String min;//最低温度
    }
    public class Wind{
        @SerializedName("deg")
        public String deg;//风向
        @SerializedName("sc")
        public String sc;//风力等级
    }
    public class Cond{
        @SerializedName("txt_d")
        public String txtd;
        @SerializedName("txt_n")
        public String txtn;
    }

}
