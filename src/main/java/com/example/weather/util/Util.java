package com.example.weather.util;



import android.text.TextUtils;
import android.util.Log;


import com.example.weather.db.City;
import com.example.weather.db.County;
import com.example.weather.db.Province;
import com.example.weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/1/17.
 */

public class Util  {

    public static boolean SelectProvinceResponse(String response){
        //Log.i("MainActivity", "SelectProvinceResponse: "+response.toString());
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Province p = new Province();
                    JSONObject projson = jsonArray.getJSONObject(i);
                    p.setProvinceCode(projson.getInt("id"));
                    p.setProvinceName(projson.getString("name"));
                    p.save();
                }
                Log.i("MainActivity", "SelectProvinceResponse: "+111);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }

    public static boolean SelectCityResponse(String response,int provinceid){
        if(!TextUtils.isEmpty(response)){
        try {
            JSONArray jsonArray=new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                City city=new City();
                city.setCityCode(jsonObject.getInt("id"));
                city.setCityName(jsonObject.getString("name"));
                city.setProvinceID(provinceid);
                city.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
        return  false;
    }

    public static boolean SelectCountyResponse(String response,int cityid){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray array=new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject=array.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(jsonObject.getString("name"));
                    county.setCityId(cityid);
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static Weather handlerWeatherResponse(String response){

        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray array=jsonObject.getJSONArray("HeWeather5");
            String weathercontent=array.getJSONObject(0).toString();
            //Log.i("MainActivity", "handlerWeatherResponse: "+weathercontent);
            return new Gson().fromJson(weathercontent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public static boolean isNight(){
        Calendar calendar=Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.i("MainActivity", "isNight: "+hour);
        if(hour<=19&&hour>=7){
            return false;
        }else{
            return true;
        }

    }
}
