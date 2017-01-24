package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Suggesstion {
    @SerializedName("comf")
    public Comf comf;
    @SerializedName("cw")
    public Cw cw;
    @SerializedName("drsg")
    public Drsg drsg;
    @SerializedName("sport")
    public Sport sport;
    @SerializedName("uv")
    public Uv uv;



    //舒适指数
    public class Comf{
        @SerializedName("brf")
        public String Brf;//简介
        @SerializedName("txt")
        public String txt;//详细描述
    }

    //洗车指数
    public class Cw{
        @SerializedName("brf")
        public String Brf;//简介
        @SerializedName("txt")
        public String txt;//详细描述
    }

    //穿衣指数
    public class Drsg{
        @SerializedName("brf")
        public String Brf;//简介
        @SerializedName("txt")
        public String txt;//详细描述
    }
    //运动指数
    public class Sport{
        @SerializedName("brf")
        public String Brf;//简介
        @SerializedName("txt")
        public String txt;//详细描述v
    }
    //紫外线指数
    public class Uv{
        @SerializedName("brf")
        public String Brf;//简介
        @SerializedName("txt")
        public String txt;//详细描述
    }
}
