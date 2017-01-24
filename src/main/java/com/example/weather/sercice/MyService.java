package com.example.weather.sercice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.weather.gson.Weather;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requestWeather();

        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=4*60*60*1000;//
        long time = SystemClock.elapsedRealtime() + anHour;
        Intent intent1=new Intent(this,MyService.class);
        PendingIntent pintent1=PendingIntent.getService(this,0,intent1,0);
        manager.cancel(pintent1);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,time,pintent1);
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestWeather() {
        final SharedPreferences preferences  = getSharedPreferences("weather", MODE_PRIVATE);
        String weather = preferences.getString("weather", null);
        String weatherId="";
        if(weather!=null){
            weatherId= Util.handlerWeatherResponse(weather).basic.weatherID;
        }
        String weatherUrl="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=46f365323eb94498ab007f8fd403973f";
        HttpUtil.sendOkhttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Weather weather1 = Util.handlerWeatherResponse(string);
                if(weather1!=null&&weather1.equals("ok")){
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("weather",string);
                    edit.commit();
                }
            }
        });
    }


}
